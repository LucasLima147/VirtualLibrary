package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Editora;

public interface EditoraService {

	List<Editora> readAll();

	Editora readById(int id);

	int create(Editora entity);

	boolean update(Editora entity);

	boolean delete(int id);
}
