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

import br.fai.vl.api.service.EntregaService;
import br.fai.vl.dto.EntregaDTO;
import br.fai.vl.model.Entrega;

@RestController
@RequestMapping("/api/v1/entrega")
public class EntregaRestController {

	@Autowired
	private EntregaService service;

	@GetMapping("/read-all")
	public ResponseEntity<List<Entrega>> readAll() {

		return ResponseEntity.ok(service.readAll());
	}

	@GetMapping("/read-by-id/{id}")
	public ResponseEntity<Entrega> readById(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.readById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<Integer> create(@RequestBody final Entrega entity) {

		return ResponseEntity.ok(service.create(entity));
	}

	@PutMapping("/update")
	public ResponseEntity<Boolean> update(@RequestBody final Entrega entity) {

		return ResponseEntity.ok(service.update(entity));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.delete(id));
	}

	@GetMapping("/check-delivery-request/{idEmprestimo}/{idLeitor}")
	public ResponseEntity<String> checkDeliveryRequest(@PathVariable("idEmprestimo") final int idEmprestimo,
			@PathVariable("idLeitor") final int idLeitor) {

		return ResponseEntity.ok(service.checkDeliveryRequest(idEmprestimo, idLeitor));
	}

	@GetMapping("/delivery-order-list")
	public ResponseEntity<List<EntregaDTO>> deliveryOrderList() {

		return ResponseEntity.ok(service.deliveryOrderList());
	}

	@GetMapping("/refuse-delivery/{idEntrega}")
	public ResponseEntity<Boolean> refuseDelivery(@PathVariable("idEntrega") final int idEntrega) {

		return ResponseEntity.ok(service.refuseDelivery(idEntrega));
	}

	@GetMapping("/accept-delivery/{idEntrega}")
	public ResponseEntity<Boolean> acceptDelivery(@PathVariable("idEntrega") final int idEntrega) {

		return ResponseEntity.ok(service.acceptDelivery(idEntrega));
	}

	@GetMapping("/closed-delivery-order-list")
	public ResponseEntity<List<EntregaDTO>> closedDeliveryOrderList() {

		return ResponseEntity.ok(service.closedDeliveryOrderList());
	}
}
