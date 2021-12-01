package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.dto.RecolhimentoDTO;
import br.fai.vl.model.Recolhimento;
import br.fai.vl.web.service.RecolhimentoService;
import br.fai.vl.web.service.RestService;

@Service
public class RecolhimentoServiceImpl implements RecolhimentoService {

	@Override
	public List<Recolhimento> readAll() {

		final String endpoint = "http://localhost:8085/api/v1/recolhimento/read-all";
		List<Recolhimento> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Recolhimento[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Recolhimento[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Recolhimento readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/recolhimento/read-by-id";
		Recolhimento response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Recolhimento> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Recolhimento.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final Recolhimento entity) {
		final String endpoint = "http://localhost:8085//api/v1/recolhimento/create";
		int id = Integer.valueOf(-1);

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<Recolhimento> httpEntity = new HttpEntity<Recolhimento>(entity, headers);
			final ResponseEntity<Integer> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return id;
	}

	@Override
	public boolean update(final Recolhimento entity) {
		final String endpoint = "http://localhost:8085//api/v1/recolhimento/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Recolhimento> httpEntity = new HttpEntity<Recolhimento>(entity);
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
		final String endpoint = "http://localhost:8085//api/v1/recolhimento/delete/" + id;
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
	public String requestCollection(final int idEmprestimo, final int idLeitor) {
		final String endpoint = "http://localhost:8085//api/v1/recolhimento/request-collection/" + idEmprestimo + "/"
				+ idLeitor;
		String response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<String> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					String.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<RecolhimentoDTO> pickUpOrderList() {

		final String endpoint = "http://localhost:8085//api/v1/recolhimento/pickup-order-list";
		List<RecolhimentoDTO> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<RecolhimentoDTO[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, RecolhimentoDTO[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean refuseCollection(final int id) {
		final String endpoint = "http://localhost:8085//api/v1/recolhimento/refuse-collection/" + id;
		boolean response = false;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Boolean> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Boolean.class);

			response = requestResponse.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean acceptCollection(final int id) {
		final String endpoint = "http://localhost:8085//api/v1/recolhimento/accept-collection/" + id;
		boolean response = false;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Boolean> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Boolean.class);

			response = requestResponse.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<RecolhimentoDTO> closedPickUpOrderList() {

		final String endpoint = "http://localhost:8085//api/v1/recolhimento/closed-pickup-order-list";
		List<RecolhimentoDTO> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<RecolhimentoDTO[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, RecolhimentoDTO[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

}
