package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Bibliotecario;

public interface BibliotecarioService {

	List<Bibliotecario> readAll();

	Bibliotecario readById(int id);

	int create(Bibliotecario entity);

	boolean update(Bibliotecario entity);

	boolean delete(int id);

	boolean recoveryPasswor(int idUser, String newPassword);

	Bibliotecario validateLogin(String encodeData);

	int checkEmail(String email);
}
