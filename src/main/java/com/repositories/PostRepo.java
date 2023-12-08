package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entites.Category;
import com.entites.Post;
import com.entites.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	// Custom Methods
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> findByTitleContaining(@Param("key")String title);
	
}
