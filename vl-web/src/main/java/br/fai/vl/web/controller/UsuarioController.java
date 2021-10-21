package br.fai.vl.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Leitor;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.BibliotecarioService;
import br.fai.vl.web.service.LeitorService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private LeitorService leitorService;
	@Autowired
	private BibliotecarioService bibliotecarioService;

	@GetMapping("/editar-perfil")
	public String getEditar() {
		return "usuario/editar-perfil";
	}

	@GetMapping("/perfil-usuario")
	public String getPerfil() {

		if (!Account.isLogin()) {
			return "redirect:/leitor/entrar";
		} else {
			return "redirect:/leitor/detail/" + Account.getIdUser();
		}
	}

	@GetMapping("/notificacao")
	public String getNotificacao() {
		return "usuario/notificacao";
	}

	@GetMapping("/notificacao-adm")
	public String getNotificacaoAdm() {
		return "usuario/notificacao-adm";
	}

	@GetMapping("/carrinho")
	public String getCarrinho() {
		return "usuario/carrinho";
	}

	@GetMapping("/list")
	public String getList(final Model model) {

		final List<Leitor> leitores = leitorService.readAll();
		model.addAttribute("listaDeLeitores", leitores);

		final List<Bibliotecario> bibliotecarios = bibliotecarioService.readAll();
		model.addAttribute("listaDeBibliotecarios", bibliotecarios);

		return "usuario/list";
	}

	@GetMapping("/detail")
	public String getDetail() {
		return "usuario/detail";
	}

}
