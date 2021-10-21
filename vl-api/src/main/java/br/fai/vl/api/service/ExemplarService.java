package br.fai.vl.api.service;

import java.util.List;

import br.fai.vl.model.Exemplar;

public interface ExemplarService {

	List<Exemplar> readAll();

	Exemplar readById(int id);

	int create(Exemplar entity);

	boolean update(Exemplar entity);

	boolean delete(int id);
}
