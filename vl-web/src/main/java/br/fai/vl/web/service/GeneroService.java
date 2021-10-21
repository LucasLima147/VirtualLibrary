package br.fai.vl.web.service;

import java.util.List;

import br.fai.vl.model.Genero;

public interface GeneroService {

	List<Genero> readAll();

	Genero readById(int id);

	int create(Genero entity);

	boolean update(Genero entity);

	boolean delete(int id);
}
