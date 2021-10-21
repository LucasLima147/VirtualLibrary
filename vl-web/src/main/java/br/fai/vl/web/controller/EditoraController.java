package br.fai.vl.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Editora;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.EditoraService;

@Controller
@RequestMapping("/editora")
public class EditoraController {

	@Autowired
	private EditoraService service;

	@GetMapping("/list")
	private String getEditoraList(final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			System.out.println(Account.getPermissionLevel());
			if (Account.getPermissionLevel() >= 2) {
				final List<Editora> editoras = service.readAll();
				model.addAttribute("listaDeEditoras", editoras);

				return "editora/list";
			} else {
				return "redirect:/account/entrar";
			}
		}

	}

	@GetMapping("/edit/{id}")
	private String getEditoraEdit(@PathVariable final int id, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 2) {
				final Editora editora = service.readById(id);
				model.addAttribute("editaEditora", editora);

				return "editora/edit";
			} else {
				return "redirect:/account/entrar";
			}
		}
	}

	@PostMapping("/update")
	private String update(final Editora editora, final Model model) {
		service.update(editora);

		return "redirect:/editora/list";
	}

	@GetMapping("/register-editora")
	private String getRegisterEditora(final Editora editora) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 2) {

				return "editora/create";
			} else {
				return "redirect:/account/entrar";
			}
		}

	}

	@PostMapping("/create")
	private String createEditora(final Editora editora, final Model model) {

		final int id = service.create(editora);

		if (id != -1) {
			return "redirect:/editora/list";
		} else {
			return "/editora/create";
		}
	}

	@GetMapping("/delete/{id}")
	private String deleteEditora(@PathVariable final int id, final Model model) {

		if (!Account.isLogin()) {
			return "redirect:/account/entrar";
		} else {
			if (Account.getPermissionLevel() >= 2) {

				service.delete(id);
				return getEditoraList(model);
			} else {
				return "redirect:/account/entrar";
			}
		}

	}
}
