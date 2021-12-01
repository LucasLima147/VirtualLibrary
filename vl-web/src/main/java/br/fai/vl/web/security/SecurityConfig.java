package br.fai.vl.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.fai.vl.web.security.provider.VlAuthenticationProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private VlAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/").permitAll()
			// account
			.antMatchers("/account/forgot-my-passowrd").permitAll()
			.antMatchers("/account/list").hasRole("BIBLIOTECARIO")
			.antMatchers("/account/nova-senha").permitAll()
			.antMatchers("/account/check-email").permitAll()
			.antMatchers("/account/recovery-passowrd").permitAll()
			.antMatchers("/account/recuperar-senha").permitAll()
			.antMatchers("/account/my-loans").hasRole("LEITOR")
			.antMatchers("/account/remove-Loan-Book/**/**").hasRole("LEITOR")
			.antMatchers("/account/terminate-loan/**").hasRole("LEITOR")
			.antMatchers("/account/notificacao").hasRole("LEITOR")
			// leitor
			.antMatchers("/leitor/register").permitAll()
			.antMatchers("/leitor/detail/**").hasAnyRole("LEITOR", "BIBLIOTECARIO")
			.antMatchers("/leitor/edit/**").hasAnyRole("LEITOR", "BIBLIOTECARIO")
			.antMatchers("/leitor/update").hasAnyRole("LEITOR", "BIBLIOTECARIO")
			.antMatchers("/leitor/delete/**").hasRole("BIBLIOTECARIO")
			// bibliotecario
			.antMatchers("/bibliotecario/register").hasAnyRole("BIBLIOTECARIO")
			.antMatchers("/bibliotecario/detail/**").hasAnyRole("BIBLIOTECARIO")
			.antMatchers("/bibliotecario/edit/**").hasAnyRole("BIBLIOTECARIO")
			.antMatchers("/bibliotecario/update").hasAnyRole("BIBLIOTECARIO")
			.antMatchers("/bibliotecario/delete/**").hasRole("BIBLIOTECARIO")
			// livro
			.antMatchers("/livro/list").permitAll()
			.antMatchers("/livro/list-adm").hasRole("BIBLIOTECARIO")
			.antMatchers("/livro/edit/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/livro/detail/**").permitAll()
			.antMatchers("/livro/finalizar-emprestimo/**").hasRole("LEITOR")
			.antMatchers("/livro/emprestrar-livro/**/**").hasRole("LEITOR")
			// editora
			.antMatchers("/editora/list").hasRole("BIBLIOTECARIO")
			.antMatchers("/editora/edit/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/editora/delete/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/editora/register-editora").hasRole("BIBLIOTECARIO")
			.antMatchers("/editora/update").hasRole("BIBLIOTECARIO")
			// emprestimo
			.antMatchers("/emprestimo/list").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/devolver-exemplar/**/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/entregas").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/recolhimento").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/list").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/entregas").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/request-user-loan/**/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/aceitar-entrega/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/recusar-entrega/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/recolhimento").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/aceitar-recolhimento/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/recusar-recolhimento/**").hasRole("BIBLIOTECARIO")
			.antMatchers("/emprestimo/solicitar-entrega/**").hasRole("LEITOR")
			.antMatchers("/emprestimo/solicitar-recolhimento/**").hasRole("LEITOR")
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/account/entrar").permitAll()
			.loginProcessingUrl("/login").permitAll()
			.defaultSuccessUrl("/")
		.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/get-out"))
			.logoutSuccessUrl("/")
		.and()
		.exceptionHandling().accessDeniedPage("/not-found");
	}
}
