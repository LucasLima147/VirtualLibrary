package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.dto.RecolhimentoDTO;
import br.fai.vl.model.Recolhimento;

public interface RecolhimentoDao {
	List<Recolhimento> readAll();

	Recolhimento readById(int id);

	int create(Recolhimento entity);

	boolean update(Recolhimento entity);

	boolean delete(int id);

	Recolhimento requestCollection(int idEmprestimo, int idLeitor);

	List<RecolhimentoDTO> pickUpOrderList();

	boolean refuseCollection(int idRecolhimento);

	boolean acceptCollection(int idRecolhimento);

	List<RecolhimentoDTO> closedPickUpOrderList();
}
