package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Leitor;

public interface LeitorDao {

	List<Leitor> readAll();

	Leitor readById(int id);

	int create(Leitor entity);

	boolean update(Leitor entity);

	boolean delete(int id);

	int checkEmail(String email);

	boolean recoveryPasswor(int idUser, String newPassword);
	
	Leitor validateUsernameAndPassword(final String username, final String password);
}
