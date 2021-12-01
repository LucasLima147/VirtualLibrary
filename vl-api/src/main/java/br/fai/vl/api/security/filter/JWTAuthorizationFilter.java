package br.fai.vl.api.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.fai.vl.api.security.ApiSecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {

		try {
			if (!checkJwtToken(request)) {
				SecurityContextHolder.clearContext();
				return;
			}

			final Jws<Claims> claims = validateToken(request);

			if (claims == null || claims.getBody().get("authorities") == null) {
				SecurityContextHolder.clearContext();
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Você não pode acessar esse recurso");
				return;
			}
			
			setupAuthentication(claims.getBody());

		} catch (final Exception e) {
			// para caso não passar dentro do filtro
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Você não pode acessar esse recurso");

			System.out.println(e.getMessage());
		} finally {
			filterChain.doFilter(request, response);
		}

	}

	private boolean checkJwtToken(final HttpServletRequest request) {

		final String authenticationHeader = request.getHeader(ApiSecurityConstants.HEADER);

		if (authenticationHeader == null || !authenticationHeader.startsWith(ApiSecurityConstants.PREFIX)) {
			return false;
		}

		return true;
	}

	// Claims -> o que o usuário pode fazer
	private Jws<Claims> validateToken(final HttpServletRequest request) {

		final String jwtToken = request.getHeader(ApiSecurityConstants.HEADER).replace(ApiSecurityConstants.PREFIX, "");

		return Jwts.parserBuilder().setSigningKey(ApiSecurityConstants.KEY).build().parseClaimsJws(jwtToken);
	}

	private void setupAuthentication(final Claims claims) {

		final List<String> authorities = (List<String>) claims.get("authorities");
		final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}
