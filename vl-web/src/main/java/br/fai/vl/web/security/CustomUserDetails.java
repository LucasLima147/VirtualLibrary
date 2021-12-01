package br.fai.vl.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.fai.vl.model.Pessoa;

public class CustomUserDetails extends User{
	
	private final Pessoa user;
	
	public CustomUserDetails(final String username, final String password, final Collection<? extends GrantedAuthority> authorities, final Pessoa user) {

		super(username, password, true, true, true, true,authorities);

		this.user = user;
	}

	public Pessoa getUsuario() {
		return user;
	}

}
