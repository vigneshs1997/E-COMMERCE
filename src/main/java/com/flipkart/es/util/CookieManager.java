package com.flipkart.es.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
@Component//=>for creating bean object
public class CookieManager {
    
	@Value("${myapp.domain}")
	private String domain;
	
	public Cookie configure(Cookie cookie, int expirationInSeconds) {
	      cookie.setDomain(domain);	
	      cookie.setSecure(false);
	      cookie.setHttpOnly(true);
	      cookie.setPath("/");
	      cookie.setMaxAge(expirationInSeconds);
	      
	      return cookie;
	      
	}
//to invalidate a particular cookie
	
	public Cookie invalidate(Cookie cookie) {
		
		cookie.setPath("/");
		cookie.setMaxAge(0);
		return cookie;
	}
}
