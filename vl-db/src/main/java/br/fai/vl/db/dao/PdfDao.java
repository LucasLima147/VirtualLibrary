package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Autor;

public interface PdfDao {

	List<Autor> readAll();

	Autor readById(int id);

	int create(Autor entity);

	boolean update(Autor entity);

	boolean delete(int id);
}
