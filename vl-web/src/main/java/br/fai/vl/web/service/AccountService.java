package br.fai.vl.web.service;

import br.fai.vl.model.Pessoa;

public interface AccountService {

	boolean checkLogin(int pageAccessLevel);

	void disconnect();

	Pessoa validateUsernameAndPassword(String username, String password, boolean isBibliotecario);
}
