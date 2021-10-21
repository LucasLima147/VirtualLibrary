package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.AutorService;
import br.fai.vl.db.dao.AutorDao;
import br.fai.vl.model.Autor;

@Service
public class AutorServiceImpl implements AutorService {

	@Autowired
	private AutorDao dao;

	@Override
	public List<Autor> readAll() {
		return dao.readAll();
	}

	@Override
	public Autor readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Autor entity) {
		// TODO Auto-generated method stub
		return dao.create(entity);
	}

	@Override
	public boolean update(final Autor entity) {
		// TODO Auto-generated method stub
		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {
		// TODO Auto-generated method stub
		return dao.delete(id);
	}

}
