package br.fai.vl.web.service;

public interface AccountService {

	boolean checkLogin(int pageAccessLevel);

	void disconnect();

}
