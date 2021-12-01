package br.fai.vl.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.fai.vl.api.security.filter.JWTAuthorizationFilter;

@EnableWebSecurity
@Configuration
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.csrf().disable().addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				// account -> login
				.antMatchers(HttpMethod.POST, "/api/account/login/leitor").permitAll()
				.antMatchers(HttpMethod.POST, "/api/account/login/bibliotecario").permitAll()
				// leitor
				.antMatchers(HttpMethod.POST, "/api/v1/leitor/create").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/leitor/read-all").hasAnyRole("LEITOR", "BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/leitor/read-by-id/**").hasAnyRole("LEITOR", "BIBLIOTECARIO")
				.antMatchers(HttpMethod.PUT, "/api/v1/leitor/update").hasAnyRole("LEITOR", "BIBLIOTECARIO")
				.antMatchers(HttpMethod.DELETE, "/api/v1/leitor/delete/**").hasAnyRole("LEITOR", "BIBLIOTECARIO")
				.antMatchers(HttpMethod.POST, "/api/v1/leitor/check-mail").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/leitor/recovery-password").permitAll()
				// bibliotecario
				.antMatchers(HttpMethod.POST, "/api/v1/bibliotecario/create").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/bibliotecario/read-all").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/bibliotecario/read-by-id/**").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.PUT, "/api/v1/bibliotecario/update").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.DELETE, "/api/v1/bibliotecario/delete/**").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.POST, "/api/v1/bibliotecario/check-mail").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/bibliotecario/recovery-password").permitAll()
				// livro
				.antMatchers(HttpMethod.GET, "/api/v1/livro/read-all").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/livro/read-by-id/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/livro/create").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.PUT, "/api/v1/livro/update").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.DELETE, "/api/v1/livro/delete/**").hasRole("BIBLIOTECARIO")
				// editora
				.antMatchers(HttpMethod.GET, "/api/v1/editora/read-all").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/editora/read-by-id/**").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.POST, "/api/v1/editora/create").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.POST, "/api/v1/editora/update").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/editora/delete/**").hasRole("BIBLIOTECARIO")
				// autor
				.antMatchers(HttpMethod.GET, "/api/v1/autor/read-all").hasRole("BIBLIOTECARIO")
				// genero
				.antMatchers(HttpMethod.GET, "/api/v1/genero/read-all").hasRole("BIBLIOTECARIO")
				// entrega
				.antMatchers(HttpMethod.GET, "/api/v1/entrega/closed-delivery-order-list").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/entrega/delivery-order-list").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/entrega/accept-delivery/**").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/entrega/refuse-delivery/**").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/entrega/check-delivery-request/**/**").hasAnyRole("BIBLIOTECARIO", "LEITOR")
				.antMatchers(HttpMethod.POST, "/api/v1/entrega/create").hasAnyRole("LEITOR")
				// recolhimento
				.antMatchers(HttpMethod.GET, "/api/v1/recolhimento/pickup-order-list").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/recolhimento/request-collection/**/**").hasAnyRole("BIBLIOTECARIO", "LEITOR")
				.antMatchers(HttpMethod.GET, "/api/v1/recolhimento/closed-pickup-order-list").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/recolhimento/accept-collection/**").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/recolhimento/refuse-collection/**").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.POST, "/api/v1/recolhimento/create").hasRole("LEITOR")
				// emprestimo
				.antMatchers(HttpMethod.GET, "/api/v1/emprestimo/check-loan/**/**").hasAnyRole("BIBLIOTECARIO", "LEITOR")
				.antMatchers(HttpMethod.GET, "/api/v1/emprestimo/return-copy/**/**").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/emprestimo/close-loans-list").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.GET, "/api/v1/emprestimo/open-loans-list").hasRole("BIBLIOTECARIO")
				.antMatchers(HttpMethod.POST, "/api/v1/emprestimo/create/**/**").hasRole("LEITOR")
				.antMatchers(HttpMethod.GET, "/api/v1/emprestimo/open-user-loams/**").hasRole("LEITOR")
				.antMatchers(HttpMethod.POST, "/api/v1/emprestimo/remove-loan-book").hasRole("LEITOR")
				.antMatchers(HttpMethod.DELETE, "/api/v1/emprestimo/delete/**").hasRole("LEITOR")
				.antMatchers(HttpMethod.GET, "/api/v1/emprestimo/terminate-loan/**").hasRole("LEITOR")
				.antMatchers(HttpMethod.GET, "/api/v1/emprestimo/last-loan-record/**").hasRole("LEITOR")
				.antMatchers(HttpMethod.GET, "/api/v1/emprestimo/my-previousLoans/**").hasRole("LEITOR")
			.anyRequest().authenticated();
	}
}
