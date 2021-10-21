package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.model.Administrador;
import br.fai.vl.web.service.AdministradorService;

public class AdministradorServiceImpl implements AdministradorService {

	@Override
	public List<Administrador> readAll() {
		final String endpoint = "http://localhost:8085/api/v1/administrador/read-all";
		List<Administrador> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Administrador[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Administrador[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Administrador readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/administrador/read-by-id";
		Administrador response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Administrador> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Administrador.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean update(final Administrador entity) {
		final String endpoint = "http://localhost:8085//api/v1/leitor/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Administrador> httpEntity = new HttpEntity<Administrador>(entity);
			final ResponseEntity<Boolean> responseEntity = restTemplace.exchange(endpoint, HttpMethod.PUT, httpEntity,
					Boolean.class);
			response = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

}
