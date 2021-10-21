package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.LivroService;
import br.fai.vl.db.dao.LivroDao;
import br.fai.vl.model.Livro;

@Service
public class LivroServiceImpl implements LivroService {

	@Autowired
	private LivroDao dao;

	@Override
	public List<Livro> readAll() {

		return dao.readAll();
	}

	@Override
	public Livro readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Livro entity) {

		return dao.create(entity);
	}

	@Override
	public boolean update(final Livro entity) {

		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {

		return dao.delete(id);
	}

}
