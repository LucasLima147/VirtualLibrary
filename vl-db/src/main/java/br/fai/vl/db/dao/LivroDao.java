package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Livro;

public interface LivroDao {

	List<Livro> readAll();

	Livro readById(int id);

	int create(Livro entity);

	boolean update(Livro entity);

	boolean delete(int id);
}
