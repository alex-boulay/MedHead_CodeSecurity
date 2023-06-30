package com.ocal.medheadsecurity.config;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;
import lombok.Getter;

import java.security.Key;
import com.ocal.medheadsecurity.service.KeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JWTGenerator {
	
	private final KeyService keyService;
	
	private Key key;
	
	@Autowired
	public JWTGenerator(KeyService keyService) {
		this.keyService = keyService;
		this.key = keyService.readSecretKeyFromFile();
	}
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPRITATION);
		
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
		String token = Jwts.builder()
				.setSubject(username)
				.claim("roles", roles)
				.setIssuedAt( new Date())
				.setExpiration(expireDate)
				.signWith(getKey(),SignatureAlgorithm.HS512)
				.compact();
		return token;
	}
	
	
	public String getUsernameFromJWT(String token){
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect");
		}
	}
	
	public Key getKey() {
		return key;
	}

}
