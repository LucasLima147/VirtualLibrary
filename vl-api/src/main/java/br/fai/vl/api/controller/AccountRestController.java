package br.fai.vl.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fai.vl.api.service.AccountService;
import br.fai.vl.model.Pessoa;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountRestController {

	@Autowired
	private AccountService accountService;

	@PostMapping("/login")
	public ResponseEntity<Pessoa> loginLeitor(@RequestHeader("Authorization") final String encodeData) {

		final Pessoa usuario = accountService.validateLogin(encodeData);

		if (usuario == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(usuario);
	}
}
