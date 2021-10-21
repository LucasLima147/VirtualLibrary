package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.GeneroService;
import br.fai.vl.db.dao.GeneroDao;
import br.fai.vl.model.Genero;

@Service
public class GeneroServiceImpl implements GeneroService {

	@Autowired
	private GeneroDao dao;

	@Override
	public List<Genero> readAll() {
		return dao.readAll();
	}

	@Override
	public Genero readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Genero entity) {
		// TODO Auto-generated method stub
		return dao.create(entity);
	}

	@Override
	public boolean update(final Genero entity) {
		// TODO Auto-generated method stub
		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {
		// TODO Auto-generated method stub
		return dao.delete(id);
	}

}
