package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.model.Bibliotecario;
import br.fai.vl.model.Pessoa;
import br.fai.vl.web.model.Account;
import br.fai.vl.web.service.BibliotecarioService;
import br.fai.vl.web.service.RestService;

@Service
public class BibliotecarioServiceImpl implements BibliotecarioService {

	@Override
	public List<Bibliotecario> readAll() {

		final String endpoint = "http://localhost:8085/api/v1/bibliotecario/read-all";
		List<Bibliotecario> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Bibliotecario[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Bibliotecario[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Bibliotecario readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/bibliotecario/read-by-id/" + id;
		Bibliotecario response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Bibliotecario> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Bibliotecario.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final Bibliotecario entity) {
		final String endpoint = "http://localhost:8085/api/v1/bibliotecario/create";
		int id = Integer.valueOf(-1);

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Bibliotecario> httpEntity = new HttpEntity<Bibliotecario>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return id;
	}

	@Override
	public boolean update(final Bibliotecario entity) {
		final String endpoint = "http://localhost:8085/api/v1/bibliotecario/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade

			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<Bibliotecario> httpEntity = new HttpEntity<Bibliotecario>(entity, headers);
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
		final String endpoint = "http://localhost:8085//api/v1/bibliotecario/delete/" + id;
		boolean response = false;

		try {
			final RestTemplate restTemplace = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Boolean> requestResponse = restTemplace.exchange(endpoint, HttpMethod.DELETE,
					httpEntity, Boolean.class);

			response = requestResponse.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int checkEmail(final Bibliotecario entity) {
		final String endpoint = "http://localhost:8085/api/v1/bibliotecario/check-mail";
		int id = Integer.valueOf(-1);

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Bibliotecario> httpEntity = new HttpEntity<Bibliotecario>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		if (id != -1) {
			Account.setIdUserRecoveryPassword(id);
			Account.setTypeUserRecoveryPassword(2);
		}

		return id;
	}

	@Override
	public boolean recoveryPasswor(final Bibliotecario entity) {
		final String endpoint = "http://localhost:8085/api/v1/bibliotecario/recovery-password";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Bibliotecario> httpEntity = new HttpEntity<Bibliotecario>(entity);
			final ResponseEntity<Boolean> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Boolean.class);
			response = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

}
