package com.flipkart.es.security;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.w3c.dom.ranges.RangeException;

import com.flipkart.es.entity.AccessToken;
import com.flipkart.es.exception.FailedToAuthenticateException;
import com.flipkart.es.repository.AccessTokenRepo;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j //=>for system.out.print
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private CustomUserDetailService customUserDetailService ;
	private AccessTokenRepo accessTokenRepo;
	private JwtService jwtService; 
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws java.io.IOException, ServletException {

		String at=null;
		String rt=null;
		Cookie[] cookies =request.getCookies();
		if(cookies != null) 
		{
			for(Cookie cookie: cookies) {

				if(cookie.getName().equals("at")) at =cookie.getValue();
				if(cookie.getName().equals("rt")) rt =cookie.getValue();
			}

			String username=null;
			if(at != null && rt != null) {
				Optional<AccessToken> accessToken = accessTokenRepo.findByTokenAndIsBlocked(at, false);

				if(accessToken == null) throw new NullPointerException("access token not available");
				else {
					log.info("Authenticating the token");//@Slf4j => if log is to be worked;
					username=jwtService.extractUsername(at);
					if(username == null)throw new FailedToAuthenticateException();
					UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null,userDetails.getAuthorities());
					token.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(token);
					log.info("Authenticated successfully");

				}

			}
		}
		filterChain.doFilter(request, response);//doFilter is a predefined method
	}
}
