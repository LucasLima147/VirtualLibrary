package br.fai.vl.web.service;

import java.util.List;

import br.fai.vl.model.Leitor;

public interface LeitorService {

	List<Leitor> readAll();

	Leitor readById(int id);

	int create(Leitor entity);

	boolean update(Leitor entity);

	boolean delete(int id);

	boolean login(Leitor entity);

	int checkEmail(Leitor entity);

	boolean recoveryPasswor(Leitor entity);
}
