package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Bibliotecario;

public interface BibliotecarioService {

	List<Bibliotecario> readAll();

	Bibliotecario readById(int id);

	int login(String email, String password);

	int create(Bibliotecario entity);

	boolean update(Bibliotecario entity);

	boolean delete(int id);

	int checkEmail(String email);

	boolean recoveryPasswor(int idUser, String newPassword);
}
