package br.fai.vl.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fai.vl.api.service.BibliotecarioService;
import br.fai.vl.model.Bibliotecario;

@RestController
@RequestMapping("/api/v1/bibliotecario")
public class BibliotecarioRestController {

	@Autowired
	private BibliotecarioService service;

	@GetMapping("/read-all")
	public ResponseEntity<List<Bibliotecario>> readAll() {

		return ResponseEntity.ok(service.readAll());
	}

	@GetMapping("/read-by-id/{id}")
	public ResponseEntity<Bibliotecario> readById(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.readById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<Integer> create(@RequestBody final Bibliotecario entity) {

		return ResponseEntity.ok(service.create(entity));
	}

	@PutMapping("/update")
	public ResponseEntity<Boolean> update(@RequestBody final Bibliotecario entity) {

		return ResponseEntity.ok(service.update(entity));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.delete(id));
	}

	@PostMapping("/login")
	public ResponseEntity<Integer> login(@RequestBody final Bibliotecario entity) {

		return ResponseEntity.ok(service.login(entity.getEmail(), entity.getSenha()));
	}

	@PostMapping("/check-mail")
	public ResponseEntity<Integer> checkEmail(@RequestBody final Bibliotecario entity) {

		return ResponseEntity.ok(service.checkEmail(entity.getEmail()));
	}

	@PostMapping("/recovery-password")
	public ResponseEntity<Boolean> recoveryPasswor(@RequestBody final Bibliotecario entity) {

		return ResponseEntity.ok(service.recoveryPasswor(entity.getId(), entity.getSenha()));
	}
}
