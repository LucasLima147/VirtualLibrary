package br.fai.vl.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fai.vl.api.service.BibliotecarioService;
import br.fai.vl.api.service.JwtService;
import br.fai.vl.db.dao.BibliotecarioDao;
import br.fai.vl.model.Bibliotecario;

@Service
public class BibliotecarioServiceImpl implements BibliotecarioService {

	@Autowired
	private BibliotecarioDao dao;
	@Autowired
	private JwtService jwtService;

	@Override
	public List<Bibliotecario> readAll() {

		return dao.readAll();
	}

	@Override
	public Bibliotecario readById(final int id) {

		return dao.readById(id);
	}

	@Override
	public int create(final Bibliotecario entity) {
		return dao.create(entity);
	}

	@Override
	public boolean update(final Bibliotecario entity) {
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
	public Bibliotecario validateLogin(final String encodeData) {
		final Map<CREDENCIAIS, String> credentialMap = decodeAndGetUsernameAndPassword(encodeData);

		if (credentialMap == null || credentialMap.size() != 2) {
			return null;
		}

		final String username = credentialMap.get(CREDENCIAIS.USUARIO);
		final String password = credentialMap.get(CREDENCIAIS.SENHA);

		final Bibliotecario bibliotecario = dao.validateUsernameAndPassword(username, password);

		if (bibliotecario == null) {
			return null;
		}

		final String token = jwtService.getJwtToken(bibliotecario);
		bibliotecario.setToken(token);
		bibliotecario.setSenha(null);

		return bibliotecario;
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
