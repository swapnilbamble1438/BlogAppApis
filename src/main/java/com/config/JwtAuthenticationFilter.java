package com.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.MalformedJwtException;

/*
 1. Create Jwt authenticationEntryPoint implements AuthenticationEntryPoint
 2. Create JwtTokenHelper
 3. JwtAuthenticationFilter extends OnceRequestFilter
	 i.   Get jwt token from request
	 ii.  Validate token
	 iii. Get user from token
	 iv.  Load user associated with token
	 v.   Set Spring Security
 4. Create JwtAuthResponse
 5. Configure JWT in spring security config	 
 */

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenHelper jwtUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// get jwt token
		//check if it starting from Bearer
		//validate
		
	String requestTokenHeader = request.getHeader("Authorization");
	
	String username = null;
	String jwtToken = null;
	if(requestTokenHeader!= null && requestTokenHeader.startsWith("Bearer "))
	{
		jwtToken = requestTokenHeader.substring(7); // it will remove Bearer from
		System.out.println("jwtToken:" + jwtToken);
		try {
			username = jwtUtil.extractUsername(jwtToken);
			System.out.println("username: " + username);
		}catch(IllegalArgumentException e)
		{
			System.out.println("Unable to get Jwt token");
		}
		catch(MalformedJwtException e)
		{
			System.out.println("Invalid Jwt");
			
		}
	}
	else
	{
		System.out.println("Jwt token does not begin with Bearer");
	}
	
	
		 //security
		if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			 UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
 
			 System.out.println("name: "+ userDetails.getUsername());
			 System.out.println("password: "+ userDetails.getPassword());
			 
			 if(jwtUtil.validateToken(jwtToken, userDetails))
			 {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =	new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			
			}
			else {
				System.out.println("Token is not validate");
			}
		
		
	}
		else {
			System.out.println("username is null or context is not null");
		}
	filterChain.doFilter(request, response);
		
		
	}

}
