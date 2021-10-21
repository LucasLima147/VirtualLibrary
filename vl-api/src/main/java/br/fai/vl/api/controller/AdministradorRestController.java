package br.fai.vl.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fai.vl.api.service.AdministradorService;
import br.fai.vl.model.Administrador;

@RestController
@RequestMapping("/api/v1/administrador")
public class AdministradorRestController {

	@Autowired
	private AdministradorService service;

	@GetMapping("/read-all")
	public ResponseEntity<List<Administrador>> readAll() {

		return ResponseEntity.ok(service.readAll());
	}

	@GetMapping("/read-by-id/{id}")
	public ResponseEntity<Administrador> readById(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.readById(id));
	}

	@PutMapping("/update")
	public ResponseEntity<Boolean> update(@RequestBody final Administrador entity) {

		return ResponseEntity.ok(service.update(entity));
	}

}
