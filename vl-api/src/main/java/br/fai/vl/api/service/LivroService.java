package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Livro;

public interface LivroService {

	List<Livro> readAll();

	Livro readById(int id);

	int create(Livro entity);

	boolean update(Livro entity);

	boolean delete(int id);
}
