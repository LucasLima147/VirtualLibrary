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

import br.fai.vl.api.service.AutorService;
import br.fai.vl.model.Autor;

@RestController
@RequestMapping("/api/v1/autor")
public class AutorRestController {

	@Autowired
	private AutorService service;

	@GetMapping("/read-all")
	public ResponseEntity<List<Autor>> readAll() {

		return ResponseEntity.ok(service.readAll());
	}

	@GetMapping("/read-by-id/{id}")
	public ResponseEntity<Autor> readById(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.readById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<Integer> create(@RequestBody final Autor entity) {

		return ResponseEntity.ok(service.create(entity));
	}

	@PutMapping("/update")
	public ResponseEntity<Boolean> update(@RequestBody final Autor entity) {

		return ResponseEntity.ok(service.update(entity));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.delete(id));
	}
}
