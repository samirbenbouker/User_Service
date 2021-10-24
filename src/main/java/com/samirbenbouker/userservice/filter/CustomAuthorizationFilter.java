package com.samirbenbouker.userservice.filter;

import java.io.IOException;
import java.util.ArrayList;
import static java.util.Arrays.stream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	// Take the permissions for users
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(request.getServletPath().equals("/api/v1/login") || request.getServletPath().equals("/api/v1/token/refresh")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			
			// Login do it succesfully
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					Algorithm algorithm = Algorithm.HMAC256("secrect".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(authorizationHeader.substring("Bearer".length()));

					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
					
					stream(roles).forEach(role -> {
						authorities.add(new SimpleGrantedAuthority(role));
					});
					
					SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities));
					filterChain.doFilter(request, response);
				} catch(Exception e) {
					throw new IllegalStateException(e.getMessage());
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
