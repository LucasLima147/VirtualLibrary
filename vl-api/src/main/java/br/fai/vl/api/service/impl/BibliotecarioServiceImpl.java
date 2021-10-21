package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.BibliotecarioService;
import br.fai.vl.db.dao.BibliotecarioDao;
import br.fai.vl.model.Bibliotecario;

@Service
public class BibliotecarioServiceImpl implements BibliotecarioService {

	@Autowired
	private BibliotecarioDao dao;

	@Override
	public List<Bibliotecario> readAll() {

		return dao.readAll();
	}

	@Override
	public Bibliotecario readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Bibliotecario entity) {
		return dao.create(entity);
	}

	@Override
	public boolean update(final Bibliotecario entity) {
		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {
		return dao.delete(id);
	}

	@Override
	public int login(final String email, final String password) {
		final int idBibliotecario = Integer.valueOf(-1);
		final List<Bibliotecario> bibliotecarios = dao.login();

		for (final Bibliotecario bibliotecario : bibliotecarios) {
			if (bibliotecario.getEmail().equals(email) && bibliotecario.getSenha().equals(password)) {
				return bibliotecario.getId();
			}
		}

		return idBibliotecario;
	}

	@Override
	public int checkEmail(final String email) {
		// TODO Auto-generated method stub
		return dao.checkEmail(email);
	}

	@Override
	public boolean recoveryPasswor(final int idUser, final String newPassword) {
		// TODO Auto-generated method stub
		return dao.recoveryPasswor(idUser, newPassword);
	}
}
