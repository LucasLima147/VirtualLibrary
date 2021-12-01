package br.fai.vl.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.JwtService;
import br.fai.vl.api.service.LeitorService;
import br.fai.vl.db.dao.LeitorDao;
import br.fai.vl.model.Leitor;

@Service
public class LeitorServiceImpl implements LeitorService {

	@Autowired
	private LeitorDao dao;
	@Autowired
	private JwtService jwtService;

	@Override
	public List<Leitor> readAll() {
		return dao.readAll();
	}

	@Override
	public Leitor readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Leitor entity) {

		return dao.create(entity);
	}

	@Override
	public boolean update(final Leitor entity) {
		return dao.update(entity);
	}

	@Override
	public boolean delete(final int id) {
		return dao.delete(id);
	}

	@Override
	public int checkEmail(final String email) {
		return dao.checkEmail(email);
	}

	@Override
	public boolean recoveryPasswor(final int idUser, final String newPassword) {
		// TODO Auto-generated method stub
		return dao.recoveryPasswor(idUser, newPassword);
	}
	
	private enum CREDENCIAIS {
		USUARIO, // 0
		SENHA, // 1
	}

	@Override
	public Leitor validateLogin(final String encodeData) {
		final Map<CREDENCIAIS, String> credentialMap = decodeAndGetUsernameAndPassword(encodeData);

		if (credentialMap == null || credentialMap.size() != 2) {
			return null;
		}

		final String username = credentialMap.get(CREDENCIAIS.USUARIO);
		final String password = credentialMap.get(CREDENCIAIS.SENHA);

		final Leitor leitor = dao.validateUsernameAndPassword(username, password);

		if (leitor == null) {
			return null;
		}

		final String token = jwtService.getJwtToken(leitor);
		System.out.println(token);
		leitor.setToken(token);
		leitor.setSenha(null);

		return leitor;
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
