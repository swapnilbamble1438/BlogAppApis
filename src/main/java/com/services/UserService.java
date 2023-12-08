package com.services;

import java.util.List;
import java.util.Optional;

import com.entites.User;
import com.payloads.UserDto;
import com.payloads.UserDto2;

public interface UserService {

	UserDto registerNewUser(UserDto userDto);
	
	UserDto  createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto, Integer userId);
	
	UserDto updateUser2(UserDto userDto, Integer userId);
	
	UserDto updateUser3(UserDto2 userDto2, Integer userId);

	UserDto getUserById(Integer userId);
		
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
	Optional<User> getUserByEmail(String email);
	
	
	
	
}
