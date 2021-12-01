package br.fai.vl.api.service;

import br.fai.vl.model.Pessoa;

public interface JwtService {
	String getJwtToken(Pessoa pessoa);
}
