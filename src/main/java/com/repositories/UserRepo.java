package com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entites.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	
	// custom methods
	
	Optional<User> findByEmail(String email);
	
	@Query("select u from User u where u.email = :email")
	public User getUserEmail(@Param("email") String username);
	
	
	@Query("select u from User u where u.email = :email and u.id != :id")
	public User getUserEmailnId(@Param("email") String username,@Param("id") int id);
	
	
	
}
