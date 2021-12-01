package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.model.Autor;
import br.fai.vl.web.service.AutorService;
import br.fai.vl.web.service.RestService;

@Service
public class AutorServiceImpl implements AutorService {

	@Override
	public List<Autor> readAll() {

		final String endpoint = "http://localhost:8085/api/v1/autor/read-all";
		List<Autor> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Autor[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Autor[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Autor readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/autor/read-by-id";
		Autor response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Autor> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Autor.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final Autor entity) {
		final String endpoint = "http://localhost:8085//api/v1/autor/create";
		int id = Integer.valueOf(-1);

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Autor> httpEntity = new HttpEntity<Autor>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return id;
	}

	@Override
	public boolean update(final Autor entity) {
		final String endpoint = "http://localhost:8085//api/v1/autor/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Autor> httpEntity = new HttpEntity<Autor>(entity);
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
		final String endpoint = "http://localhost:8085//api/v1/autor/delete/" + id;
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

}
