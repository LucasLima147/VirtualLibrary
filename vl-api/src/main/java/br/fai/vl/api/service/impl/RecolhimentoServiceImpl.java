package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.RecolhimentoService;
import br.fai.vl.db.dao.RecolhimentoDao;
import br.fai.vl.dto.RecolhimentoDTO;
import br.fai.vl.model.Recolhimento;

@Service
public class RecolhimentoServiceImpl implements RecolhimentoService {

	@Autowired
	private RecolhimentoDao dao;

	@Override
	public List<Recolhimento> readAll() {

		return dao.readAll();
	}

	@Override
	public Recolhimento readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Recolhimento entity) {

		return dao.create(entity);
	}

	@Override
	public boolean update(final Recolhimento entity) {

		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {

		return dao.delete(id);
	}

	@Override
	public String requestCollection(final int idEmprestimo, final int idLeitor) {
		final Recolhimento recolhimento = dao.requestCollection(idEmprestimo, idLeitor);

		if (recolhimento != null) {
			if (recolhimento.getDataRecolhimento() == null) {
				if (recolhimento.isRecolhido() == false) {
					return "Solicitação reprovada";
				} else {
					return "Solicitação em aprovação";
				}

			} else {
				return "Previsão de Recolhimento: " + recolhimento.getDataRecolhimento();
			}
		} else {
			return "Recolhimento não solicitado";
		}
	}

	@Override
	public List<RecolhimentoDTO> pickUpOrderList() {

		return dao.pickUpOrderList();
	}

	@Override
	public boolean refuseCollection(final int idRecolhimento) {
		// TODO Auto-generated method stub
		return dao.refuseCollection(idRecolhimento);
	}

	@Override
	public boolean acceptCollection(final int idRecolhimento) {
		// TODO Auto-generated method stub
		return dao.acceptCollection(idRecolhimento);
	}

	@Override
	public List<RecolhimentoDTO> closedPickUpOrderList() {
		// TODO Auto-generated method stub
		return dao.closedPickUpOrderList();
	}

}
