package br.fai.vl.db.dao;

import java.util.List;

import br.fai.vl.model.Administrador;

public interface AdministradorDao {

	List<Administrador> readAll();

	Administrador readById(int id);

	boolean update(Administrador entity);
}
