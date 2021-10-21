package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.EditoraService;
import br.fai.vl.db.dao.EditoraDao;
import br.fai.vl.model.Editora;

@Service
public class EditoraServiceImpl implements EditoraService {

	@Autowired
	private EditoraDao dao;

	@Override
	public List<Editora> readAll() {
		// TODO Auto-generated method stub
		return dao.readAll();
	}

	@Override
	public Editora readById(final int id) {
		// TODO Auto-generated method stub
		return dao.readById(id);
	}

	@Override
	public int create(final Editora entity) {
		// TODO Auto-generated method stub
		return dao.create(entity);
	}

	@Override
	public boolean update(final Editora entity) {
		// TODO Auto-generated method stub
		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {
		// TODO Auto-generated method stub
		return dao.delete(id);
	}

}
