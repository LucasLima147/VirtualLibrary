package br.fai.vl.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.fai.vl.model.Autor;
import br.fai.vl.model.Editora;
import br.fai.vl.model.Genero;
import br.fai.vl.model.Leitor;
import br.fai.vl.model.Livro;
import br.fai.vl.web.security.provider.VlAuthenticationProvider;
import br.fai.vl.web.service.AutorService;
import br.fai.vl.web.service.EditoraService;
import br.fai.vl.web.service.EmprestimoService;
import br.fai.vl.web.service.GeneroService;
import br.fai.vl.web.service.LeitorService;
import br.fai.vl.web.service.LivroService;

@Controller
@RequestMapping("/livro")
public class LivroController {

	boolean erro = false;

	@Autowired
	private LivroService service;
	@Autowired
	private GeneroService generoService;
	@Autowired
	private AutorService autorService;
	@Autowired
	private EditoraService editoraService;
	@Autowired
	private LeitorService leitorService;
	@Autowired
	private EmprestimoService emprestimoService;

	@Autowired
	private VlAuthenticationProvider authenticationProvider;

	@GetMapping("/list")
	public String getAcervo(final Model model) {
		final List<Livro> livroList = service.readAll();
		model.addAttribute("listaDeLivros", livroList);
		return "livro/acervo";
	}

	@GetMapping("/list-adm")
	public String getAcervoAdm(final Model model) {
		final List<Livro> livroList = service.readAll();
		model.addAttribute("listaDeLivros", livroList);
		return "livro/acervo-adm";

	}

	@GetMapping("/detail/{id}")
	public String getDescricaoLivro(@PathVariable final int id, final Model model) {
		final Livro livro = service.readById(id);

		model.addAttribute("error", erro);
		model.addAttribute("detalheDoLivro", livro);
		model.addAttribute("idLivro", id);

		erro = false;

		return "livro/descricao-livro";
	}

	@GetMapping("/finalizar-emprestimo/{idLivro}")
	public String getFinalizarEmprestimo(@PathVariable final int idLivro, final Model model) {

		final Leitor leitor = leitorService.readById(authenticationProvider.getAuthenticatedUser().getId());

		model.addAttribute("leitor", leitor.getId());
		model.addAttribute("dadosDoUsuario", leitor);

		return "livro/finalizar-emprestimo";

	}

	@GetMapping("/register")
	public String getRegisterLivro(final Model model, final Livro livro) {

		final List<Genero> generos = generoService.readAll();
		model.addAttribute("generoList", generos);

		final List<Autor> autores = autorService.readAll();
		model.addAttribute("autorList", autores);

		final List<Editora> editoras = editoraService.readAll();
		model.addAttribute("editoraList", editoras);

		return "livro/criar-livro";

	}

	@PostMapping("/create")
	private String create(final Livro livro, final Model model, final Integer numeroDeLivros) {
		final int id = service.create(livro);

		if (id != -1) {
			return "redirect:/livro/detail/" + id;
		} else {
			return "redirect:/livro/register";
		}
	}

	@GetMapping("/edit/{id}")
	public String getEditarLivro(@PathVariable final int id, final Model model) {

		final List<Genero> generos = generoService.readAll();
		model.addAttribute("generoList", generos);

		final List<Autor> autores = autorService.readAll();
		model.addAttribute("autorList", autores);

		final List<Editora> editoras = editoraService.readAll();
		model.addAttribute("editoraList", editoras);

		final Livro livro = service.readById(id);
		model.addAttribute("livro", livro);

		return "livro/editar-livro";
	}

	@PostMapping("/update")
	private String update(final Livro livro, final Model model) {
		service.update(livro);

		return "redirect:/livro/detail/" + livro.getId();
	}

	@GetMapping("/delete/{id}")
	private String delete(@PathVariable final int id, final Model model) {
		service.delete(id);

		return "redirect:/livro/list-adm";
	}

	@PostMapping("/emprestrar-livro/{idLivro}/{leitorId}")
	public String getEnviarLivro(@PathVariable final int idLivro, @PathVariable final int leitorId, final Model model) {

		final int id = emprestimoService.create(idLivro, leitorId);
		if (id != -1) {
			erro = false;
			return "redirect:/account/my-loans";
		} else {
			erro = true;
			return "redirect:/livro/detail/" + idLivro;
		}

	}
}
