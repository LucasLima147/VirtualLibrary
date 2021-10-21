package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.model.Exemplar;
import br.fai.vl.web.service.ExemplarService;

@Service
public class ExemplarServiceImpl implements ExemplarService {

	@Override
	public List<Exemplar> readAll() {

		final String endpoint = "http://localhost:8085/api/v1/exemplar/read-all";
		List<Exemplar> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Exemplar[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Exemplar[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Exemplar readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/exemplar/read-by-id";
		Exemplar response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Exemplar> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Exemplar.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final Exemplar entity) {
		final String endpoint = "http://localhost:8085//api/v1/exemplar/create";
		int id = Integer.valueOf(-1);

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Exemplar> httpEntity = new HttpEntity<Exemplar>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return id;
	}

	@Override
	public boolean update(final Exemplar entity) {
		final String endpoint = "http://localhost:8085//api/v1/exemplar/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Exemplar> httpEntity = new HttpEntity<Exemplar>(entity);
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
		final String endpoint = "http://localhost:8085//api/v1/exemplar/delete/" + id;
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
