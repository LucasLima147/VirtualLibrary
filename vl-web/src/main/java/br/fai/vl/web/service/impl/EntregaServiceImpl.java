package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.dto.EntregaDTO;
import br.fai.vl.model.Entrega;
import br.fai.vl.web.service.EntregaService;

@Service
public class EntregaServiceImpl implements EntregaService {

	@Override
	public List<Entrega> readAll() {

		final String endpoint = "http://localhost:8085/api/v1/entrega/read-all";
		List<Entrega> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Entrega[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Entrega[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Entrega readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/entrega/read-by-id" + id;
		Entrega response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Entrega> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Entrega.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final Entrega entity) {
		final String endpoint = "http://localhost:8085/api/v1/entrega/create";
		int id = Integer.valueOf(-1);

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Entrega> httpEntity = new HttpEntity<Entrega>(entity);
			final ResponseEntity<Integer> responseEntity = restTemplace.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return id;
	}

	@Override
	public boolean update(final Entrega entity) {
		final String endpoint = "http://localhost:8085//api/v1/entrega/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Entrega> httpEntity = new HttpEntity<Entrega>(entity);
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
		final String endpoint = "http://localhost:8085//api/v1/entrega/delete/" + id;
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
	public String checkDeliveryRequest(final int idEmprestimo, final int idLeitor) {
		final String endpoint = "http://localhost:8085/api/v1/entrega/check-delivery-request/" + idEmprestimo + "/"
				+ idLeitor;
		String response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<String> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					String.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<EntregaDTO> deliveryOrderList() {

		final String endpoint = "http://localhost:8085/api/v1/entrega/delivery-order-list";
		List<EntregaDTO> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<EntregaDTO[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, EntregaDTO[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean refuseDelivery(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/entrega/refuse-delivery/" + id;
		boolean response = false;

		try {
			final RestTemplate restTemplace = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Boolean> requestResponse = restTemplace.exchange(endpoint, HttpMethod.GET, httpEntity,
					Boolean.class);

			response = requestResponse.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean acceptDelivery(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/entrega/accept-delivery/" + id;
		boolean response = false;

		try {
			final RestTemplate restTemplace = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Boolean> requestResponse = restTemplace.exchange(endpoint, HttpMethod.GET, httpEntity,
					Boolean.class);

			response = requestResponse.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<EntregaDTO> closedDeliveryOrderList() {

		final String endpoint = "http://localhost:8085/api/v1/entrega/closed-delivery-order-list";
		List<EntregaDTO> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<EntregaDTO[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, EntregaDTO[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

}
