package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Leitor;

public interface LeitorService {

	List<Leitor> readAll();

	Leitor readById(int id);

	int login(String email, String password);

	int create(Leitor entity);

	boolean update(Leitor entity);

	boolean delete(int id);

	int checkEmail(String email);

	boolean recoveryPasswor(int idUser, String newPassword);

}
