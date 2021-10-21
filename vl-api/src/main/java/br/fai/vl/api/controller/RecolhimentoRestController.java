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

import br.fai.vl.api.service.RecolhimentoService;
import br.fai.vl.dto.RecolhimentoDTO;
import br.fai.vl.model.Recolhimento;

@RestController
@RequestMapping("/api/v1/recolhimento")
public class RecolhimentoRestController {

	@Autowired
	private RecolhimentoService service;

	@GetMapping("/read-all")
	public ResponseEntity<List<Recolhimento>> readAll() {

		return ResponseEntity.ok(service.readAll());
	}

	@GetMapping("/read-by-id/{id}")
	public ResponseEntity<Recolhimento> readById(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.readById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<Integer> create(@RequestBody final Recolhimento entity) {

		return ResponseEntity.ok(service.create(entity));
	}

	@PutMapping("/update")
	public ResponseEntity<Boolean> update(@RequestBody final Recolhimento entity) {

		return ResponseEntity.ok(service.update(entity));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.delete(id));
	}

	@GetMapping("/request-collection/{idEmprestimo}/{idLeitor}")
	public ResponseEntity<String> requestCollection(@PathVariable("idEmprestimo") final int idEmprestimo,
			@PathVariable("idLeitor") final int idLeitor) {

		return ResponseEntity.ok(service.requestCollection(idEmprestimo, idLeitor));
	}

	@GetMapping("/pickup-order-list")
	public ResponseEntity<List<RecolhimentoDTO>> pickUpOrderList() {

		return ResponseEntity.ok(service.pickUpOrderList());
	}

	@GetMapping("/refuse-collection/{idRecolhimento}")
	public ResponseEntity<Boolean> refuseCollection(@PathVariable("idRecolhimento") final int idRecolhimento) {

		return ResponseEntity.ok(service.refuseCollection(idRecolhimento));
	}

	@GetMapping("/accept-collection/{idRecolhimento}")
	public ResponseEntity<Boolean> acceptCollection(@PathVariable("idRecolhimento") final int idRecolhimento) {

		return ResponseEntity.ok(service.acceptCollection(idRecolhimento));
	}

	@GetMapping("/closed-pickup-order-list")
	public ResponseEntity<List<RecolhimentoDTO>> closedPickUpOrderList() {

		return ResponseEntity.ok(service.closedPickUpOrderList());
	}
}
