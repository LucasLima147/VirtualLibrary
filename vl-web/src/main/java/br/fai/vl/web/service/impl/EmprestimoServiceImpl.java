package br.fai.vl.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fai.vl.dto.EmprestimoDTO;
import br.fai.vl.model.Emprestimo;
import br.fai.vl.web.service.EmprestimoService;
import br.fai.vl.web.service.RestService;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

	@Override
	public List<Emprestimo> readAll() {

		final String endpoint = "http://localhost:8085/api/v1/emprestimo/read-all";
		List<Emprestimo> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Emprestimo[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Emprestimo[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Emprestimo readById(final int id) {
		final String endpoint = "http://localhost:8085/api/v1/emprestimo/read-by-id";
		Emprestimo response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpEntity<String> httpEntity = new HttpEntity<String>("");
			final ResponseEntity<Emprestimo> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Emprestimo.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public int create(final int idLivro, final int idUser) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/create/" + idLivro + "/" + idUser;
		int id = Integer.valueOf(-1);

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Integer> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity,
					Integer.class);
			id = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return id;
	}

	@Override
	public boolean update(final Emprestimo entity) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/update";
		boolean response = false;

		try {
			// faz a chamada da API
			final RestTemplate restTemplace = new RestTemplate();
			// receber minha entidade
			final HttpEntity<Emprestimo> httpEntity = new HttpEntity<Emprestimo>(entity);
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
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/delete/" + id;
		boolean response = false;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Boolean> requestResponse = restTemplate.exchange(endpoint, HttpMethod.DELETE,
					httpEntity, Boolean.class);

			response = requestResponse.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<EmprestimoDTO> checkOpenUserLoans(final int idLeitor) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/open-user-loams/" + idLeitor;
		List<EmprestimoDTO> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<EmprestimoDTO[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, EmprestimoDTO[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean terminateLoan(final int idEmprestimo) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/terminate-loan/" + idEmprestimo;
		boolean response = false;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Boolean> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Boolean.class);
			response = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean removeLoanBook(final EmprestimoDTO entity) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/remove-loan-book";
		boolean response = false;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<EmprestimoDTO> httpEntity = new HttpEntity<EmprestimoDTO>(entity, headers);
			final ResponseEntity<Boolean> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, httpEntity,
					Boolean.class);
			response = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public Emprestimo lastLoanRecord(final int id) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/last-loan-record/" + id;
		Emprestimo response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Emprestimo> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Emprestimo.class);

			response = requestResponse.getBody();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<Emprestimo> myPreviousLoans(final int idLeitor) {

		final String endpoint = "http://localhost:8085//api/v1/emprestimo/my-previousLoans/" + idLeitor;
		List<Emprestimo> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Emprestimo[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, Emprestimo[].class);

			response = Arrays.asList(requestResponse.getBody());
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<EmprestimoDTO> checkLoan(final int idEmprestimo, final int idUser) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/check-loan/" + idEmprestimo + "/" + idUser;
		List<EmprestimoDTO> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<EmprestimoDTO[]> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, EmprestimoDTO[].class);

			response = Arrays.asList(responseEntity.getBody());

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<EmprestimoDTO> openLoansList() {
		final String endpoint = "http://localhost:8085/api/v1/emprestimo/open-loans-list";
		List<EmprestimoDTO> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<EmprestimoDTO[]> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, EmprestimoDTO[].class);

			response = Arrays.asList(responseEntity.getBody());

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public boolean returnCopy(final int idExemplar, final int idEmprestimo) {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/return-copy/" + idExemplar + "/"
				+ idEmprestimo;
		boolean response = false;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<Boolean> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					Boolean.class);

			response = responseEntity.getBody();

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

	@Override
	public List<EmprestimoDTO> closeLoansList() {
		final String endpoint = "http://localhost:8085//api/v1/emprestimo/close-loans-list";
		List<EmprestimoDTO> response = null;

		try {
			final RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = RestService.getRequestHeaders();
			final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			final ResponseEntity<EmprestimoDTO[]> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET,
					httpEntity, EmprestimoDTO[].class);

			response = Arrays.asList(responseEntity.getBody());

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

		return response;
	}
}
