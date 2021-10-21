package br.fai.vl.web.service;

import java.util.List;

import br.fai.vl.model.Bibliotecario;

public interface BibliotecarioService {

	List<Bibliotecario> readAll();

	Bibliotecario readById(int id);

	int create(Bibliotecario entity);

	boolean update(Bibliotecario entity);

	boolean delete(int id);

	boolean login(Bibliotecario entity);

	int checkEmail(Bibliotecario entity);

	boolean recoveryPasswor(Bibliotecario entity);
}