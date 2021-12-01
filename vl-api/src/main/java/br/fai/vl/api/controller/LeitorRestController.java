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

import br.fai.vl.api.service.LeitorService;
import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Leitor;

@RestController
@RequestMapping("/api/v1/leitor")
public class LeitorRestController {

	@Autowired
	private LeitorService service;

	@GetMapping("/read-all")
	public ResponseEntity<List<Leitor>> readAll() {

		return ResponseEntity.ok(service.readAll());
	}

	@GetMapping("/read-by-id/{id}")
	public ResponseEntity<Leitor> readById(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.readById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<Integer> create(@RequestBody final Leitor entity) {

		return ResponseEntity.ok(service.create(entity));
	}

	@PutMapping("/update")
	public ResponseEntity<Boolean> update(@RequestBody final Leitor entity) {

		return ResponseEntity.ok(service.update(entity));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.delete(id));
	}
	
	@PostMapping("/check-mail")
	public ResponseEntity<Integer> checkMail(@RequestBody final Bibliotecario entity) {

		return ResponseEntity.ok(service.checkEmail(entity.getEmail()));
	}

	@PostMapping("/recovery-password")
	public ResponseEntity<Boolean> recoveryPasswor(@RequestBody final Leitor entity) {

		return ResponseEntity.ok(service.recoveryPasswor(entity.getId(), entity.getSenha()));
	}
}
