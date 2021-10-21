package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.model.Editora;
import br.fai.vl.web.service.EditoraService;

@Service
public class EditoraServiceImpl implements EditoraService {

	@Override
	public List<Editora> readAll() {

		final String endpoint = "http://localhost:8085//api/v1/editora/read-all";
		List<Editora> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Editora[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Editora[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Editora readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/editora/read-by-id/" + id;
		Editora response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Editora> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Editora.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final Editora entity) {
		final String endpoint = "http://localhost:8085//api/v1/editora/create";
		int id = Integer.valueOf(-1);

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Editora> httpEntity = new HttpEntity<Editora>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return id;
	}

	@Override
	public boolean update(final Editora entity) {
		final String endpoint = "http://localhost:8085//api/v1/editora/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Editora> httpEntity = new HttpEntity<Editora>(entity);
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
		final String endpoint = "http://localhost:8085//api/v1/editora/delete/" + id;
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
