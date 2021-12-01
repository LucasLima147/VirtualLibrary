package br.fai.vl.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import br.fai.vl.api.security.ApiSecurityConstants;
import br.fai.vl.api.service.JwtService;
import br.fai.vl.model.Pessoa;
import io.jsonwebtoken.Jwts;

@Service
public class JwtServiceImpl implements JwtService{
	@Override
	public String getJwtToken(Pessoa pessoa) {
		
		try {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+pessoa.getTipo()));
			
			String token = Jwts.builder()
					.setId("VL_FAI")
					.setSubject(pessoa.getNome())
					.claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList()))
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 600000))
					.signWith(ApiSecurityConstants.KEY).compact();
			
			System.out.println("Novo token: " + token);
			return token;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ApiSecurityConstants.INVALID_TOKEN;
		} 
	}
}
