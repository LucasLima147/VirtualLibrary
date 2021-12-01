package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Editora;

public interface EditoraDao {

	List<Editora> readAll();

	int create(Editora entity);

	boolean update(Editora entity);

	boolean delete(int id);

	Editora readById(int id);
}
