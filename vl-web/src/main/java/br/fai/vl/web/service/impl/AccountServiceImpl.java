package br.fai.vl.web.service.impl;

import org.springframework.stereotype.Service;

import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Override
	public boolean checkLogin(final int pageAccessLevel) {

		if (Account.isLogin() && Account.getPermissionLevel() >= pageAccessLevel) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void disconnect() {
		Account.setIdUser(-1);
		Account.setLogin(false);
		Account.setPermissionLevel(0);
	}

}
