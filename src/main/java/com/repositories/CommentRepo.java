package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entites.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

	
}
