package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.dto.EntregaDTO;
import br.fai.vl.model.Entrega;

public interface EntregaDao {

	List<Entrega> readAll();

	Entrega readById(int id);

	int create(Entrega entity);

	boolean update(Entrega entity);

	boolean delete(int id);

	Entrega checkDeliveryRequest(final int idEmprestimo, final int idLeitor);

	List<EntregaDTO> deliveryOrderList();

	boolean refuseDelivery(int idEntrega);

	boolean acceptDelivery(int idEntrega);

	List<EntregaDTO> closedDeliveryOrderList();
}
