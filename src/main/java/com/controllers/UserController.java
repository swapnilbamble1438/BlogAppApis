package com.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payloads.ApiResponse;
import com.payloads.UserDto;
import com.payloads.UserDto2;
import com.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	// POST - create user
	/*	{
		    "name": "Swapnil",
		    "email":"swapnil@gmail.com",
		    "password": "1234",
		    "about":"I am a Programmer"

		}
	*/
	// PUT - update user
	// DELETE - delete user
	// GET - get user

	@Autowired
	private UserService userService;
	
	@PostMapping("/create")   //@Valid is used for activating working of validator
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUserDto = userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId)
	{
		UserDto updatedUser = userService.updateUser(userDto, uId);
		
		return ResponseEntity.ok(updatedUser);	
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
	{
		userService.deleteUser(uid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	
	}
	
	@GetMapping("/getall")
	public  ResponseEntity<List<UserDto>> getAllUsers()
	{
		
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public  ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer uId)
	{
		UserDto userDto = userService.getUserById(uId);
		
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
	@PutMapping("/update2/{userId}")
	public ResponseEntity<UserDto> updateUser2(@Valid @RequestBody UserDto2 userDto2,@PathVariable("userId") Integer uId)
	{
		UserDto updatedUser = userService.updateUser3(userDto2, uId);
		
		return ResponseEntity.ok(updatedUser);	
	}
	
	
}
