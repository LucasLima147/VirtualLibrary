package br.fai.vl.web.security.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.fai.vl.model.Pessoa;
import br.fai.vl.web.security.CustomUserDetails;
import br.fai.vl.web.service.AccountService;

@Component
public class VlAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private AccountService accountService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		boolean isBibliotecario = false;
		final String username = authentication.getName();
		final String password = authentication.getCredentials().toString();

		System.out.println("Username: " + username + " | Password: " + password);

		if (username.contains("@vituallibrary.com")) {
			isBibliotecario = true;
		}

		final Pessoa user = accountService.validateUsernameAndPassword(username, password, isBibliotecario);

		if (user == null) {
			return null;
		}

		final List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
		grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + user.getTipo()));

		final UserDetails principal = new CustomUserDetails(username, password, grantedAuthorityList, user);

		return new UsernamePasswordAuthenticationToken(principal, password, grantedAuthorityList);
	}

	public Pessoa getAuthenticatedUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		return userDetails.getUsuario();
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
