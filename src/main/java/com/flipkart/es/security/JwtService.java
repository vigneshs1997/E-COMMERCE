package com.flipkart.es.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
    
	@Value("${myapp.secrete}")
	private String secret;
	
	@Value("${myapp.access.expiry}")
	private Long accessExpirationInseconds;
	
	@Value("${myapp.refresh.expiry}")
	private Long refreshExpirationInSeconds;
/*==============================================================================================================================================================*/		
	public String generateAccessToken(String username) {
		return generateJWT(new HashMap<String,Object>(),username,accessExpirationInseconds * 1000l);
	}
		
	public String generateRefreshToken(String username) {
		return generateJWT(new HashMap<String,Object>(),username,refreshExpirationInSeconds * 1000l);
	}
	
	public String extractUsername(String token) {
		return parseJWT(token).getSubject();
		
	}
	
/*==============================================================================================================================================================*/	
	private String generateJWT(Map<String, Object> claims,String username,Long expiry) {
		
		return Jwts.builder()
				.setClaims(claims)//It is a user credentials 
				.setSubject(username) 
				.setIssuedAt(new Date(System.currentTimeMillis()))//setting  local date and time as soon as token is generated
				.setExpiration(new Date(System.currentTimeMillis()+expiry))
				.signWith(getSignature(), SignatureAlgorithm.HS512)//signing the jWT with key => getSignature() is called
				.compact();
	}
	private Key getSignature(){
		
		byte[] secretBytes= Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}
	
	private Claims parseJWT(String token) {
		
		JwtParser jwtParser= Jwts.parserBuilder().setSigningKey(getSignature()).build();
		return Jwts.parserBuilder().setSigningKey(getSignature()).build().parseClaimsJws(token).getBody();
		
	}
	
}
