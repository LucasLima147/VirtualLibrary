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
import br.fai.vl.web.service.EditoraService;

@Controller
@RequestMapping("/editora")
public class EditoraController {

	@Autowired
	private EditoraService service;

	@GetMapping("/list")
	private String getEditoraList(final Model model) {
		final List<Editora> editoras = service.readAll();
		model.addAttribute("listaDeEditoras", editoras);

		return "editora/list";
	}

	@GetMapping("/edit/{id}")
	private String getEditoraEdit(@PathVariable final int id, final Model model) {
		final Editora editora = service.readById(id);
		model.addAttribute("editaEditora", editora);

		return "editora/edit";
	}

	@PostMapping("/update")
	private String update(final Editora editora, final Model model) {
		service.update(editora);

		return "redirect:/editora/list";
	}

	@GetMapping("/register-editora")
	private String getRegisterEditora(final Editora editora) {
		return "editora/create";

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
		service.delete(id);
		return getEditoraList(model);
	}
}
