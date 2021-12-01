package br.fai.vl.web.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Leitor;
import br.fai.vl.model.Pessoa;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.AccountService;
import br.fai.vl.web.service.RestService;

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
	public Pessoa validateUsernameAndPassword(final String username, final String password,
			final boolean isBibliotecario) {

		Pessoa usuario = null;
		String endpoint;
		endpoint = "http://localhost:8085//api/account/login";
		final RestTemplate restTemplace = new RestTemplate();

		try {
			RestService.getRequestHeaders();

			final HttpHeaders httpHeaders = RestService.getAuthenticationHeaders(username, password);
			final HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

			if (username.contains("@vituallibrary.com")) {
				final ResponseEntity<Bibliotecario> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST,
						httpEntity, Bibliotecario.class);

				if (responseEntity.getStatusCode() != HttpStatus.OK) {
					return null;
				}

				usuario = responseEntity.getBody();
			} else {
				final ResponseEntity<Leitor> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST,
						httpEntity, Leitor.class);

				if (responseEntity.getStatusCode() != HttpStatus.OK) {
					return null;
				}

				usuario = responseEntity.getBody();
			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return usuario;
	}

}
