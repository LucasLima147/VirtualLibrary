package br.fai.vl.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.LeitorService;
import br.fai.vl.db.dao.LeitorDao;
import br.fai.vl.model.Leitor;

@Service
public class LeitorServiceImpl implements LeitorService {

	@Autowired
	private LeitorDao dao;

	@Override
	public List<Leitor> readAll() {
		return dao.readAll();
	}

	@Override
	public Leitor readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Leitor entity) {

		return dao.create(entity);
	}

	@Override
	public boolean update(final Leitor entity) {
		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {
		return dao.delete(id);
	}

	@Override
	public int login(final String email, final String password) {
		final int idLeitor = Integer.valueOf(-1);
		final List<Leitor> leitores = dao.login();

		for (final Leitor leitor : leitores) {
			if (leitor.getEmail().equals(email) && leitor.getSenha().equals(password)) {
				return leitor.getId();
			}
		}

		return idLeitor;
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
