package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.dto.RecolhimentoDTO;
import br.fai.vl.model.Recolhimento;

public interface RecolhimentoService {

	List<Recolhimento> readAll();

	Recolhimento readById(int id);

	int create(Recolhimento entity);

	boolean update(Recolhimento entity);

	boolean delete(int id);

	String requestCollection(int idEmprestimo, int idLeitor);

	List<RecolhimentoDTO> pickUpOrderList();

	boolean refuseCollection(int idRecolhimento);

	boolean acceptCollection(int idRecolhimento);

	List<RecolhimentoDTO> closedPickUpOrderList();
}
