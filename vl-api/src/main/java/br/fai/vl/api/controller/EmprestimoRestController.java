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

import br.fai.vl.api.service.EmprestimoService;
import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Emprestimo;

@RestController
@RequestMapping("/api/v1/emprestimo")
public class EmprestimoRestController {

	@Autowired
	private EmprestimoService service;

	@GetMapping("/read-all")
	public ResponseEntity<List<Emprestimo>> readAll() {

		return ResponseEntity.ok(service.readAll());
	}

	@GetMapping("/read-by-id/{id}")
	public ResponseEntity<Emprestimo> readById(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.readById(id));
	}

	// DTOs
	// EmprestimoDTO
	// - livroId
	// - lalaId
	@PostMapping("/create/{idLivro}/{idUsuario}")
	public ResponseEntity<Integer> create(@PathVariable("idLivro") final int idLivroSolicitado,
			@PathVariable("idUsuario") final int userId) {

		return ResponseEntity.ok(service.create(idLivroSolicitado, userId));
	}

	@PutMapping("/update")
	public ResponseEntity<Boolean> update(@RequestBody final Emprestimo entity) {

		return ResponseEntity.ok(service.update(entity));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.delete(id));
	}

	@GetMapping("/open-user-loams/{idLeitor}")
	public ResponseEntity<List<EmprestimoDTO>> checkOpenUserLoans(@PathVariable("idLeitor") final int idLeitor) {

		return ResponseEntity.ok(service.checkOpenUserLoans(idLeitor));
	}

	@GetMapping("/terminate-loan/{id}")
	public ResponseEntity<Boolean> terminateLoan(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.terminateLoan(id));
	}

	@PostMapping("/remove-loan-book")
	public ResponseEntity<Boolean> removeLoanBook(@RequestBody final EmprestimoDTO entity) {

		return ResponseEntity.ok(service.removeLoanBook(entity));
	}

	@GetMapping("/last-loan-record/{id}")
	public ResponseEntity<Emprestimo> lastLoanRecord(@PathVariable("id") final int id) {

		return ResponseEntity.ok(service.lastLoanRecord(id));
	}

	@GetMapping("/my-previousLoans/{idLeitor}")
	public ResponseEntity<List<Emprestimo>> myPreviousLoans(@PathVariable("idLeitor") final int idLeitor) {

		return ResponseEntity.ok(service.myPreviousLoans(idLeitor));
	}

	@GetMapping("/check-loan/{idEmprestimo}/{idLeitor}")
	public ResponseEntity<List<EmprestimoDTO>> checkLoan(@PathVariable("idLeitor") final int idLeitor,
			@PathVariable("idEmprestimo") final int idEmprestimo) {

		return ResponseEntity.ok(service.checkLoan(idEmprestimo, idLeitor));
	}

	@GetMapping("/open-loans-list")
	public ResponseEntity<List<EmprestimoDTO>> openLoansList() {

		return ResponseEntity.ok(service.openLoansList());
	}

	@GetMapping("/return-copy/{idExemplar}/{idEmprestimo}")
	public ResponseEntity<Boolean> returnCopy(@PathVariable("idExemplar") final int idExemplar,
			@PathVariable("idEmprestimo") final int idEmprestimo) {

		return ResponseEntity.ok(service.returnCopy(idExemplar, idEmprestimo));
	}

	@GetMapping("/close-loans-list")
	public ResponseEntity<List<EmprestimoDTO>> closeLoansList() {

		return ResponseEntity.ok(service.closeLoansList());
	}
}
