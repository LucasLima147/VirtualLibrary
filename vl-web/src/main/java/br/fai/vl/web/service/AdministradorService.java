package br.fai.vl.web.service;

import java.util.List;

import br.fai.vl.model.Administrador;

public interface AdministradorService {

	List<Administrador> readAll();

	Administrador readById(int id);

	boolean update(Administrador entity);
}
