package com.shoppingcart.tree;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class jwtFilter extends OncePerRequestFilter{
	
	@Autowired
	public MyUserDetailsService _MyUserDetailsService;
	
	@Autowired
	public jwt _jwt;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
			final String autharizationheader = request.getHeader("authorization");	
			String username = null;
			String jwt = null;
			if(autharizationheader != null && autharizationheader.startsWith("Bearer ")) {
				jwt = autharizationheader.substring(7);
				username = _jwt.extractUsername(jwt);
			}
			if(username != null && SecurityContextHolder.getContext().getAuthentication() ==  null) {
				UserDetails _Details = this._MyUserDetailsService.loadUserByUsername(username);
				
				if(_jwt.validateToken(jwt, _Details)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							_Details, null, _Details.getAuthorities());
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			filterChain.doFilter(request, response);

	}

}
