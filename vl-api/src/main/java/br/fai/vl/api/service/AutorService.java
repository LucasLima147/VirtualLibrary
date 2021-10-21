package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Autor;

public interface AutorService {

	List<Autor> readAll();

	Autor readById(int id);

	int create(Autor entity);

	boolean update(Autor entity);

	boolean delete(int id);
}
