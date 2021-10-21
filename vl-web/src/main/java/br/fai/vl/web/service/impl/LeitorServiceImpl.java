package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.model.Leitor;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.LeitorService;

@Service
public class LeitorServiceImpl implements LeitorService {

	@Override
	public List<Leitor> readAll() {

		final String endpoint = "http://localhost:8085/api/v1/leitor/read-all";
		List<Leitor> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Leitor[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Leitor[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Leitor readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/leitor/read-by-id/" + id;
		Leitor response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Leitor> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Leitor.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final Leitor entity) {
		final String endpoint = "http://localhost:8085//api/v1/leitor/create";
		int id = Integer.valueOf(-1);

		try {
			final RestTemplate restTemplace = new RestTemplate();
			final HttpEntity<Leitor> httpEntity = new HttpEntity<Leitor>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		if (id != -1) {
			Account.setIdUser(id);
			Account.setLogin(true);
			Account.setPermissionLevel(1);
		}

		return id;
	}

	@Override
	public boolean update(final Leitor entity) {
		final String endpoint = "http://localhost:8085/api/v1/leitor/update";
		boolean response = false;

		System.out.println("passou no service web: " + entity.getId());

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Leitor> httpEntity = new HttpEntity<Leitor>(entity);
			final ResponseEntity<Boolean> responseEntity = restTemplace.exchange(endpoint, HttpMethod.PUT, httpEntity,
					Boolean.class);
			response = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean delete(final int id) {
		final String endpoint = "http://localhost:8085//api/v1/leitor/delete/" + id;
		boolean response = false;

		try {
			final RestTemplate restTemplace = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Boolean> requestResponse = restTemplace.exchange(endpoint, HttpMethod.DELETE,
					httpEntity, Boolean.class);

			response = requestResponse.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean login(final Leitor entity) {
		final String endpoint = "http://localhost:8085/api/v1/leitor/login";
		int id = Integer.valueOf(-1);

		try {
			final RestTemplate restTemplace = new RestTemplate();
			final HttpEntity<Leitor> httpEntity = new HttpEntity<Leitor>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		if (id != -1) {
			Account.setIdUser(id);
			Account.setLogin(true);
			Account.setPermissionLevel(1);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int checkEmail(final Leitor entity) {
		final String endpoint = "http://localhost:8085/api/v1/leitor/check-mail";
		int id = Integer.valueOf(-1);

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Leitor> httpEntity = new HttpEntity<Leitor>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		if (id != -1) {
			Account.setIdUserRecoveryPassword(id);
			Account.setTypeUserRecoveryPassword(1);
		}
		return id;
	}

	@Override
	public boolean recoveryPasswor(final Leitor entity) {
		final String endpoint = "http://localhost:8085/api/v1/leitor/recovery-password";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Leitor> httpEntity = new HttpEntity<Leitor>(entity);
			final ResponseEntity<Boolean> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Boolean.class);
			response = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

}
