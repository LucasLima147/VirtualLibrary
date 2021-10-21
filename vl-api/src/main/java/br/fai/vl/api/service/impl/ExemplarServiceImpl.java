package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.ExemplarService;
import br.fai.vl.db.dao.ExemplarDao;
import br.fai.vl.model.Exemplar;

@Service
public class ExemplarServiceImpl implements ExemplarService {

	@Autowired
	private ExemplarDao dao;

	@Override
	public List<Exemplar> readAll() {

		return dao.readAll();
	}

	@Override
	public Exemplar readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Exemplar entity) {

		return dao.create(entity);
	}

	@Override
	public boolean update(final Exemplar entity) {

		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {

		return dao.delete(id);
	}
}
