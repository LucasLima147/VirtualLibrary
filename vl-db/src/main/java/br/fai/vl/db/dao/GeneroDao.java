package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Genero;

public interface GeneroDao {

	List<Genero> readAll();

	Genero readById(int id);

	int create(Genero entity);

	boolean update(Genero entity);

	boolean delete(int id);
}
