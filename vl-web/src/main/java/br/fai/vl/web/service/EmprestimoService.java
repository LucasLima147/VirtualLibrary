package br.fai.vl.web.service;

import java.util.List;

import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Emprestimo;

public interface EmprestimoService {

	List<Emprestimo> readAll();

	Emprestimo readById(int id);

	int create(int idLivro, int idUser);

	boolean update(Emprestimo entity);

	boolean delete(int id);

	List<EmprestimoDTO> checkOpenUserLoans(final int idLeitor);

	boolean terminateLoan(int idEmprestimo);

	boolean removeLoanBook(EmprestimoDTO entity);

	Emprestimo lastLoanRecord(int id);

	List<Emprestimo> myPreviousLoans(int idLeitor);

	List<EmprestimoDTO> checkLoan(int idEmprestimo, int idLeitor);

	List<EmprestimoDTO> openLoansList();

	boolean returnCopy(int idExemplar, int idEmprestimo);

	List<EmprestimoDTO> closeLoansList();
}
