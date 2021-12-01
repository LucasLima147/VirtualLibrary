package br.fai.vl.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fai.vl.api.service.BibliotecarioService;
import br.fai.vl.api.service.LeitorService;
import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Leitor;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountRestController {

	@Autowired
	private LeitorService leitorService;
	@Autowired
	private BibliotecarioService bibliotecarioService;

	@PostMapping("/login/leitor")
	public ResponseEntity<Leitor> loginLeitor(@RequestHeader("Authorization") final String encodeData) {

		final Leitor usuario = leitorService.validateLogin(encodeData);

		if (usuario == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(usuario);
	}

	@PostMapping("/login/bibliotecario")
	public ResponseEntity<Bibliotecario> loginBibliotecario(@RequestHeader("Authorization") final String encodeData) {

		final Bibliotecario usuario = bibliotecarioService.validateLogin(encodeData);

		if (usuario == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(usuario);
	}
}
