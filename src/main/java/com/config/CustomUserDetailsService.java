package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entites.User;
import com.exceptions.ResourceNotFoundException;
import com.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//loading user from database by username
		User user = userService.getUserByEmail(username)
		.orElseThrow(()-> new ResourceNotFoundException
		("User","email: "+username,0));
		
		
		
		return new MyUserDetails(user);
	}

}
