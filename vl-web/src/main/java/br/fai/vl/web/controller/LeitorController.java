package br.fai.vl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Leitor;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.LeitorService;

@Controller
@RequestMapping("/leitor")
public class LeitorController {

	@Autowired
	private LeitorService service;

	private boolean camposCorretos = false;

	@GetMapping("/detail/{id}")
	private String getLeitorDetail(@PathVariable final int id, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 1) {

				final Leitor leitor = service.readById(id);

				model.addAttribute("tipoUsuario", "leitor");

				model.addAttribute("usuario", leitor);
				return "usuario/detail";
			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@GetMapping("/edit/{id}")
	private String getLeitorEdit(@PathVariable final int id, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 1) {

				model.addAttribute("url", "/leitor/update");

				final Leitor leitor = service.readById(id);
				model.addAttribute("user", leitor);

				return "usuario/editar-perfil";
			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@PostMapping("/update")
	private String update(final Leitor leitor, final Model model) {
		service.update(leitor);

		return "redirect:/leitor/detail/" + leitor.getId();
	}

	@GetMapping("/register")
	public String getRegister(final Model model, final Leitor livro) {

		camposCorretos = false;
		model.addAttribute("correto", camposCorretos);
		return "conta/register";
	}

	@PostMapping("/create")
	private String create(final Leitor leitor, final Model model) {

		if (leitor.getEstado().equals("-1")) {
			camposCorretos = true;
			model.addAttribute("correto", camposCorretos);
			return "conta/register";

		} else {
			final int id = service.create(leitor);

			if (id != -1) {
				camposCorretos = false;
				return "redirect:/";
			} else {
				camposCorretos = true;
				model.addAttribute("correto", camposCorretos);
				return "conta/register";
			}
		}
	}

	@GetMapping("/delete/{id}")
	private String delete(@PathVariable final int id, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/bibliotecario/entrar";
		} else {
			if (Account.getPermissionLevel() >= 1) {

				service.delete(id);
				return "redirect:/usuario/list";
			} else {
				return "redirect:/bibliotecario/entrar";
			}
		}

	}

}
