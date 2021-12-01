package br.fai.vl.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Emprestimo;
import br.fai.vl.model.Leitor;
import br.fai.vl.model.Pessoa;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.security.provider.VlAuthenticationProvider;
import br.fai.vl.web.service.BibliotecarioService;
import br.fai.vl.web.service.EmprestimoService;
import br.fai.vl.web.service.EntregaService;
import br.fai.vl.web.service.LeitorService;
import br.fai.vl.web.service.RecolhimentoService;

@Controller
@RequestMapping("/account")
public class AccountController {

	private boolean emailInvalido = false;

	@Autowired
	private LeitorService leitorService;

	@Autowired
	private BibliotecarioService bibliotecarioService;

	@Autowired
	private EmprestimoService emprestimoService;

	@Autowired
	private EntregaService entregaService;

	@Autowired
	private RecolhimentoService recolhimentoService;

	@Autowired
	private VlAuthenticationProvider authenticationProvider;

	@GetMapping("/editar-perfil")
	public String getEditar() {

		final Pessoa user = authenticationProvider.getAuthenticatedUser();

		if (user instanceof Leitor) {
			return "redirect:/leitor/edit/" + user.getId();

		} else if (user instanceof Bibliotecario) {
			return "redirect:/bibliotecario/edit/" + user.getId();
		} else {
			return "redirect:/account/entrar";
		}
	}

	@GetMapping("/perfil-usuario")
	public String getPerfil() {

		final Pessoa user = authenticationProvider.getAuthenticatedUser();

		if (user instanceof Leitor) {
			return "redirect:/leitor/detail/" + user.getId();

		} else if (user instanceof Bibliotecario) {
			return "redirect:/bibliotecario/detail/" + user.getId();
		} else {
			return "redirect:/account/entrar";
		}

	}

	@GetMapping("/notificacao")
	public String getNotificacao(final Model model) {

		final Emprestimo lastEmprestimo = emprestimoService
				.lastLoanRecord(authenticationProvider.getAuthenticatedUser().getId());
		final List<Emprestimo> emprestimoList = emprestimoService
				.myPreviousLoans(authenticationProvider.getAuthenticatedUser().getId());
		if (lastEmprestimo != null && !(emprestimoList.isEmpty())) {
			model.addAttribute("lastLoan", lastEmprestimo);

			model.addAttribute("listaDeEmprestimo", emprestimoList);

			return "usuario/notificacao";
		} else {

			final Emprestimo emprestimoProvisorio = new Emprestimo();
			emprestimoProvisorio.setCodigo(0);
			emprestimoProvisorio.setId(-1);
			model.addAttribute("lastLoan", emprestimoProvisorio);

			final List<Emprestimo> emprestimoListProvisorio = new ArrayList<Emprestimo>();

			emprestimoListProvisorio.add(emprestimoProvisorio);
			model.addAttribute("listaDeEmprestimo", emprestimoListProvisorio);

			return "usuario/notificacao";
		}
	}

	@GetMapping("/notificacao-adm")
	public String getNotificacaoAdm() {
		return "usuario/notificacao-adm";
	}

	@GetMapping("/my-loans")
	public String getOpenUserLoan(final Model model) {

		final List<EmprestimoDTO> openUserloan = emprestimoService
				.checkOpenUserLoans(authenticationProvider.getAuthenticatedUser().getId());

		if (!openUserloan.isEmpty()) {
			model.addAttribute("openLoans", openUserloan);
			model.addAttribute("idEmprestimo", openUserloan.get(0).getIdEmprestimo());
		} else {
			model.addAttribute("openLoans", null);
			model.addAttribute("idEmprestimo", -1);
		}

		return "usuario/emprestimos";
	}

	@GetMapping("/terminate-loan/{id}")
	public String terminateLoan(@PathVariable final int id) {

		if (emprestimoService.terminateLoan(id)) {
			return "redirect:/account/notificacao";
		} else {
			return "redirect:/account/entrar";
		}
	}

	@GetMapping("/remove-Loan-Book/{idEmprestimo}/{idExemplar}")
	public String removeLoanBook(@PathVariable final int idEmprestimo, @PathVariable final int idExemplar) {

		final EmprestimoDTO emprestimo = new EmprestimoDTO();
		emprestimo.setIdEmprestimo(idEmprestimo);
		emprestimo.setIdExemplar(idExemplar);

		emprestimoService.removeLoanBook(emprestimo);
		return "redirect:/account/my-loans";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable final int id) {
		emprestimoService.delete(id);
		return "redirect:/account/my-loans";
	}

// ======================= tudo sobre o Login =============================

	@GetMapping("/list")
	public String getList(final Model model) {

		final List<Leitor> leitores = leitorService.readAll();
		model.addAttribute("listaDeLeitores", leitores);

		final List<Bibliotecario> bibliotecarios = bibliotecarioService.readAll();
		model.addAttribute("listaDeBibliotecarios", bibliotecarios);

		return "usuario/list";
	}

	@GetMapping("/entrar")
	public String getEntrar() {

		return "conta/login";
	}

//	@PostMapping("/login")
//	public String

	@GetMapping("/forgot-my-passowrd")
	public String getForgotMyPassoword(final Account account) {
		return "conta/password";
	}

	@PostMapping("/check-email")
	private String checkEmail(final Account account, final Model model) {

		System.out.println("Bateu aqui");

		if (account.getLevelRequest() == 1) {
			final Leitor leitor = new Leitor();
			leitor.setEmail(account.getUserEmail());

			if (leitorService.checkEmail(leitor) != -1) {

				emailInvalido = false;
				return "redirect:/account/nova-senha";
			} else {
				emailInvalido = true;
				model.addAttribute("email", emailInvalido);
				return "conta/password";
			}

		} else if (account.getLevelRequest() == 2) {
			final Bibliotecario bibliotecario = new Bibliotecario();
			bibliotecario.setEmail(account.getUserEmail());
			if (bibliotecarioService.checkEmail(bibliotecario) != -1) {

				emailInvalido = false;
				return "redirect:/account/nova-senha";
			} else {
				emailInvalido = true;
				model.addAttribute("email", emailInvalido);
				return "conta/password";
			}
		} else {
			return "conta/password";
		}
	}

	@GetMapping("/nova-senha")
	private String getNovaSenha(final Account account) {

		return "conta/recovery-password";
	}

	@PostMapping("/recovery-passowrd")
	private String recoveryPassowrd(final Account account, final Model model) {

		if (Account.getTypeUserRecoveryPassword() == 1) {

			final Leitor leitor = new Leitor();
			leitor.setSenha(account.getUserPassword());
			leitor.setId(Account.getIdUserRecoveryPassword());

			leitorService.recoveryPasswor(leitor);

			return "redirect:/account/entrar";

		} else if (Account.getTypeUserRecoveryPassword() == 2) {

			final Bibliotecario bibliotecario = new Bibliotecario();
			bibliotecario.setSenha(account.getUserPassword());
			bibliotecario.setId(Account.getIdUserRecoveryPassword());

			bibliotecarioService.recoveryPasswor(bibliotecario);

			return "redirect:/account/entrar";
		} else {
			return "conta/recovery-password";
		}
	}

	@GetMapping("/my-previous-loans/{idEmprestimo}")
	public String getMyPreviousLoans(@PathVariable final int idEmprestimo, final Model model) {

		model.addAttribute("situacaoEntrega", entregaService.checkDeliveryRequest(idEmprestimo,
				authenticationProvider.getAuthenticatedUser().getId()));

		model.addAttribute("situacaoRecolhimento", recolhimentoService.requestCollection(idEmprestimo,
				authenticationProvider.getAuthenticatedUser().getId()));

		final List<EmprestimoDTO> openUserloan = emprestimoService.checkLoan(idEmprestimo,
				authenticationProvider.getAuthenticatedUser().getId());

		if (!openUserloan.isEmpty()) {
			model.addAttribute("loans", openUserloan);
			model.addAttribute("idEmprestimo", openUserloan.get(0).getIdEmprestimo());
		} else {
			model.addAttribute("loans", null);
			model.addAttribute("idEmprestimo", -1);
		}

		return "/usuario/fazer-solicitacoes";

	}
}
