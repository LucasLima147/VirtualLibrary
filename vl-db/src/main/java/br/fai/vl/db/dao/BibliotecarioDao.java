package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Bibliotecario;

public interface BibliotecarioDao {

	List<Bibliotecario> readAll();

	Bibliotecario readById(int id);

	List<Bibliotecario> login();

	int create(Bibliotecario entity);

	boolean update(Bibliotecario entity);

	boolean delete(int id);

	int checkEmail(String email);

	boolean recoveryPasswor(int idUser, String newPassword);
}
