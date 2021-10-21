package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Emprestimo;

public interface EmprestimoDao {

	List<Emprestimo> readAll();

	Emprestimo readById(int id);

	int create(int idLeitor, int idExemplar);

	boolean update(Emprestimo entity);

	boolean delete(int id);

	List<Integer> checkAvaliableCopies(int livroId);

	List<Integer> checkOpenReaderLoads(int leitoId);

	int addToLoads(int emprestimoId, int exemplarId);

	List<EmprestimoDTO> checkOpenUserLoans(int idLeitor);

	boolean terminateLoan(int idEmprestimo);

	boolean removeLoanBook(EmprestimoDTO entity);

	Emprestimo lastLoanRecord(int id);

	List<Emprestimo> myPreviousLoans(int idLeitor);

	List<EmprestimoDTO> checkLoan(int idEmprestimo, int idLeitor);

	List<EmprestimoDTO> openLoansList();

	boolean returnCopy(int idExemplar, int idEmprestimo);

	List<EmprestimoDTO> closeLoansList();

}
