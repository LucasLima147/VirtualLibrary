package br.fai.vl.web.service;

import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.fai.vl.model.Pessoa;
import br.fai.vl.web.security.CustomUserDetails;

public class RestService {
	
	public static HttpHeaders getAuthenticationHeaders(final String username, final String password) {

		final String auth = "Username=" + username + ";Password=" + password;

		try {

			final byte[] encodeBytes = Base64.getEncoder().encode(auth.getBytes("utf-8"));
			System.out.println("Dados em formato base64: " + new String(encodeBytes));

			final String header = "Basic " + new String(encodeBytes);

			final HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", header);

			return headers;

		} catch (final Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		
	}

	public static HttpHeaders getRequestHeaders() {

		try {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			

			final Pessoa usuarioLogado = userDetails.getUsuario();
//			System.out.println(usuarioLogado.getToken());

			final String authHeader = "Bearer " + usuarioLogado.getToken();
			System.out.println(authHeader);

			final HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", authHeader);

			return headers;

		} catch (final Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
