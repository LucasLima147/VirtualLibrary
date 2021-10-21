package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.EntregaService;
import br.fai.vl.db.dao.EntregaDao;
import br.fai.vl.dto.EntregaDTO;
import br.fai.vl.model.Entrega;

@Service
public class EntregaServiceImpl implements EntregaService {

	@Autowired
	private EntregaDao dao;

	@Override
	public List<Entrega> readAll() {

		return dao.readAll();
	}

	@Override
	public Entrega readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Entrega entity) {

		return dao.create(entity);
	}

	@Override
	public boolean update(final Entrega entity) {

		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {

		return dao.delete(id);
	}

	@Override
	public String checkDeliveryRequest(final int idEmprestimo, final int idLeitor) {

		final Entrega entrega = dao.checkDeliveryRequest(idEmprestimo, idLeitor);

		if (entrega != null) {
			if (entrega.getDataEntrega() == null) {
				if (entrega.isEntregue() == false) {
					return "Solicitação reprovada";
				} else {
					return "Solicitação em aprovação";
				}

			} else {
				return "Previsão de entrega: " + entrega.getDataEntrega();
			}
		} else {
			return "Entrega não solicitada";
		}

	}

	@Override
	public List<EntregaDTO> deliveryOrderList() {

		return dao.deliveryOrderList();
	}

	@Override
	public boolean refuseDelivery(final int idEntrega) {
		// TODO Auto-generated method stub
		return dao.refuseDelivery(idEntrega);
	}

	@Override
	public boolean acceptDelivery(final int idEntrega) {
		// TODO Auto-generated method stub
		return dao.acceptDelivery(idEntrega);
	}

	@Override
	public List<EntregaDTO> closedDeliveryOrderList() {
		// TODO Auto-generated method stub
		return dao.closedDeliveryOrderList();
	}
}
