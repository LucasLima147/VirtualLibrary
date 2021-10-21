package br.fai.vl.web.service;

import java.util.List;

import br.fai.vl.dto.EntregaDTO;
import br.fai.vl.model.Entrega;

public interface EntregaService {

	List<Entrega> readAll();

	Entrega readById(int id);

	int create(Entrega entity);

	boolean update(Entrega entity);

	boolean delete(int id);

	String checkDeliveryRequest(int idEmprestimo, int idLeitor);

	List<EntregaDTO> deliveryOrderList();

	boolean refuseDelivery(int id);

	boolean acceptDelivery(int id);

	List<EntregaDTO> closedDeliveryOrderList();
}
