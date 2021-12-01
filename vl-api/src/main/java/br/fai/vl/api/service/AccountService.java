package br.fai.vl.api.service;

import br.fai.vl.model.Pessoa;

public interface AccountService {

	Pessoa validateLogin(String encodeData);

}
