package br.fai.vl.web.service;

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

	boolean refuseCollection(int id);

	boolean acceptCollection(int id);

	List<RecolhimentoDTO> closedPickUpOrderList();
}
