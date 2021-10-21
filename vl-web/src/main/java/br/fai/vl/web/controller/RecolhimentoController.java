package br.fai.vl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Recolhimento;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.RecolhimentoService;

@Controller
@RequestMapping("/recolhimento")
public class RecolhimentoController {

	@Autowired
	private RecolhimentoService recolhimentoService;

	@GetMapping("/solicitar-recolhimento/{idEmprestimo}")
	public String terminateLoan(@PathVariable final int idEmprestimo) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() == 1) {
				final Recolhimento recolhimento = new Recolhimento();
				recolhimento.setEmprestimoId(idEmprestimo);
				recolhimento.setLeitorId(Account.getIdUser());

				recolhimentoService.create(recolhimento);

				return "redirect:/account/my-previous-loans/" + idEmprestimo;

			} else {
				return "redirect:/account/entrar";
			}
		}
	}

}
