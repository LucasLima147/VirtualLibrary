package br.fai.vl.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.AccountService;
import br.fai.vl.api.service.JwtService;
import br.fai.vl.db.dao.BibliotecarioDao;
import br.fai.vl.db.dao.LeitorDao;
import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Leitor;
import br.fai.vl.model.Pessoa;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private LeitorDao leitorDao;

	@Autowired
	private BibliotecarioDao bibliotecarioDao;

	@Autowired
	private JwtService jwtService;

	private enum CREDENCIAIS {
		USUARIO, // 0
		SENHA, // 1
	}

	@Override
	public Pessoa validateLogin(final String encodeData) {
		final Map<CREDENCIAIS, String> credentialMap = decodeAndGetUsernameAndPassword(encodeData);

		if (credentialMap == null || credentialMap.size() != 2) {
			return null;
		}

		final String username = credentialMap.get(CREDENCIAIS.USUARIO);
		final String password = credentialMap.get(CREDENCIAIS.SENHA);

		if (username.contains("@vituallibrary.com")) {
			final Bibliotecario bibliotecario = bibliotecarioDao.validateUsernameAndPassword(username, password);

			if (bibliotecario == null) {
				return null;
			}

			final String token = jwtService.getJwtToken(bibliotecario);
			System.out.println(token);
			bibliotecario.setToken(token);
			bibliotecario.setSenha(null);

			return bibliotecario;
		} else {
			final Leitor leitor = leitorDao.validateUsernameAndPassword(username, password);

			if (leitor == null) {
				return null;
			}

			final String token = jwtService.getJwtToken(leitor);
			System.out.println(token);
			leitor.setToken(token);
			leitor.setSenha(null);

			return leitor;
		}

	}

	private Map<CREDENCIAIS, String> decodeAndGetUsernameAndPassword(final String encodeData) {

		final String[] splittedData = encodeData.split("Basic ");

		if (splittedData.length != 2) {
			return null;
		}

		final byte[] decodeBytes = Base64.getDecoder().decode(splittedData[1]);

		try {

			final String decodeString = new String(decodeBytes, "utf-8");

			final String[] firstPart = decodeString.split("Username=");

			if (firstPart.length != 2) {
				return null;
			}

			final String[] credentials = firstPart[1].split(";Password=");

			if (credentials.length != 2) {
				return null;
			}

			final Map<CREDENCIAIS, String> credentialsMap = new HashMap<CREDENCIAIS, String>();
			credentialsMap.put(CREDENCIAIS.USUARIO, credentials[0]);
			credentialsMap.put(CREDENCIAIS.SENHA, credentials[1]);

			return credentialsMap;

		} catch (final UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			return null;

		}
	}
}
