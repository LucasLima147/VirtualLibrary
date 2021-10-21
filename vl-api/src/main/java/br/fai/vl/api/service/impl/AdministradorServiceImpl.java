package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.AdministradorService;
import br.fai.vl.db.dao.AdministradorDao;
import br.fai.vl.model.Administrador;

@Service
public class AdministradorServiceImpl implements AdministradorService {

	@Autowired
	private AdministradorDao dao;

	@Override
	public List<Administrador> readAll() {
		return dao.readAll();
	}

	@Override
	public Administrador readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public boolean update(final Administrador entity) {
		// TODO Auto-generated method stub
		return dao.update(entity);
	}

}
