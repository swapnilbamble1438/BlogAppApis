
package com.services.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.config.AppConstants;
import com.entites.Role;
import com.entites.User;
import com.exceptions.ApiException;
import com.exceptions.ResourceNotFoundException;
import com.payloads.RoleDto;
import com.payloads.UserDto;
import com.payloads.UserDto2;
import com.repositories.RoleRepo;
import com.repositories.UserRepo;
import com.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		if(userRepo.getUserEmail(userDto.getEmail()) != null)
		{
			throw new ApiException("Same EmailId Already Exist, Please Try different EmailId.");
		}

		User user = DtoToUser(userDto);
		User savedUser = userRepo.save(user);
		
		return UserToDto(savedUser);
	
	}



	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		

		if(userRepo.getUserEmailnId(userDto.getEmail(), userDto.getId()) != null)
		{
			throw new ApiException("Same EmailId Already Exist, Please Try different EmailId.");
		}
		
		User user = userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());	
			  user.setPassword(passwordEncoder.encode(userDto.getPassword()));	
		user.setAbout(userDto.getAbout());
		
		User updateduser = userRepo.save(user);
	UserDto updateduserDto	= UserToDto(updateduser);
		
		return updateduserDto;
	}

	@Override
	public UserDto updateUser3(UserDto2 userDto2, Integer userId) {
		

		if(userRepo.getUserEmailnId(userDto2.getEmail(), userDto2.getId()) != null)
		{
			throw new ApiException("Same EmailId Already Exist, Please Try different EmailId.");
		}
		
		User user = userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		
		user.setName(userDto2.getName());
		user.setEmail(userDto2.getEmail());	
		user.setAbout(userDto2.getAbout());
		
		User updateduser = userRepo.save(user);
	UserDto updateduserDto	= UserToDto(updateduser);
		
		return updateduserDto;
	}


	@Override
	public UserDto getUserById(Integer userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
			
		
		return UserToDto(user);
	}
	



	@Override
	public List<UserDto> getAllUsers() {

	List<User>	users = userRepo.findAll();
	
	List<UserDto> userDtos =	users.stream().map(user->UserToDto(user)).collect(Collectors.toList());
	
	
		return userDtos;
	}



	@Override
	public void deleteUser(Integer userId) {


		User user = userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
			
		userRepo.delete(user);
	}
	
	
	public User DtoToUser(UserDto userDto)
	{
//		User user = new User();
//		user.setId(userDto.getId());           // No need to write this code
//		user.setName(userDto.getName());       // since we are using
//		user.setEmail(userDto.getEmail());     // ModelMapper
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
//		return user;
		
		User user = modelMapper.map(userDto,User.class);  // it will convert itself
				return user;
	}
	
	public UserDto UserToDto(User user)
	{
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//		return userDto;
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	
	}
	
	public RoleDto  RoleToDto(Role role)
	{
		RoleDto roleDto = modelMapper.map(role, RoleDto.class);
		return roleDto;
	}
	
	public Role DtoToRole(RoleDto roleDto)
	{
		Role role = modelMapper.map(roleDto, Role.class);
		return role;
	}



	@Override
	public Optional<User> getUserByEmail(String email) {
		
	return	userRepo.findByEmail(email);
	}



	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
//		if(userDto.getName() == null || userDto.getName().trim().equals("") ||
//			userDto.getEmail() == null || userDto.getEmail().trim().equals("") ||
//			userDto.getPassword() == null || userDto.getPassword().trim().equals("")) ||
//			userDto.getAbout() == null || userDto.getAbout().trim().equals(""))}
//		{
//			throw new ApiException("Name, Email, Password Fields are required");
//		}
		
		if(userRepo.getUserEmail(userDto.getEmail()) != null)
		{
			throw new ApiException("Same EmailId Already Exist, Please Try different EmailId.");
		}
		
	User user =	modelMapper.map(userDto, User.class);
		
	// encoded the password
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	
	// roles
	Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
	user.getRoles().add(role);
	
	User newUser = userRepo.save(user);
	
		return modelMapper.map(newUser, UserDto.class);
	}
	
	@Override
	public UserDto updateUser2(UserDto userDto, Integer userId) {
		
		if(userRepo.getUserEmailnId(userDto.getEmail(), userDto.getId()) != null)
		{
			throw new ApiException("Same EmailId Already Exist, Please Try different EmailId.");
		}
		
		User user = userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setAbout(userDto.getAbout());
		
		Set<Role> r = new HashSet<>();
		
		
		Iterator<RoleDto>  itr = userDto.getRoles().iterator();
		while(itr.hasNext())
		{
			r.add(DtoToRole(itr.next()));
		}
		
		
		user.setRoles(r);
		
		User updateduser = userRepo.save(user);
	UserDto updateduserDto	= UserToDto(updateduser);
		
		return updateduserDto;
	}

	

}
