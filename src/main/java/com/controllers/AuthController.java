package com.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.JwtTokenHelper;
import com.entites.User;
import com.exceptions.ApiException;
import com.exceptions.ResourceNotFoundException;
import com.payloads.JwtAuthRequest;
import com.payloads.JwtAuthResponse;
import com.payloads.UserDto;
import com.services.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest jwtAuthRequest ) throws Exception
	{
		
		authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
		
		UserDetails userDetails =  userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		
		String token = jwtTokenHelper.generateToken(userDetails);
		
		User user = this.userService.getUserByEmail(jwtAuthRequest.getUsername()).get();
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		JwtAuthResponse response = new JwtAuthResponse();
		
		response.setToken(token);
		response.setUser(userDto);
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception 
	{
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		
	
			try {
				authenticationManager.authenticate(authenticationToken);
			} catch (BadCredentialsException e) {
				System.out.println("Invalid Details");
				
				throw new ApiException("Incorrect username or password");
			}
		
		
	}
	
	
	// register new user api
	/* you can insert like this
	  {
		    "name":"Raj",
		    "email":"raj@gmail.com",
		    "password": "1234",
		    "about":"I am a student learning SpringBoot"
		
		}
	 */
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto registeredUser = userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
	
//	@PreAuthorize("hasRole('NORMAL')")
	@PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
	@PutMapping("/update2/{userId}")
	public ResponseEntity<UserDto> updateUser2(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId)
	{
		UserDto updatedUser = userService.updateUser2(userDto, uId);
		
		return ResponseEntity.ok(updatedUser);	
	}

}
