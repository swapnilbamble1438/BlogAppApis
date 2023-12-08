package com.services;

import java.util.List;

import com.entites.Post;
import com.payloads.PostDto;
import com.payloads.PostResponse;

public interface PostService {
	
	// create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	// update
	PostDto updatePost(PostDto postDto, Integer postId,Integer categoryId);
	
	// delete
	List<PostDto> deletePost(Integer postId);
	
	
	// get all Post
	/*
	  List<PostDto> getAllPost();
	 */
	
	//get Posts by using pagination
	/*
	List<PostDto> getAllPost(Integer pageNo,Integer pageSize);
	*/
	
	// get Posts by using pagination2
	/*
	PostResponse getAllPost(Integer pageNo,Integer pageSize);
	 */
	
	// get Posts by using pagination3
	/*
	PostResponse getAllPost(Integer pageNo,Integer pageSize,String sortBy);
	*/
	
	// get Posts by using pagination4
			PostResponse getAllPost(Integer pageNo,Integer pageSize,String sortBy,String sortDir);

	
	// get single Post
	PostDto getPostById(Integer postId);
	
	// get all Post by Category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	// get all Post by User
	List<PostDto> getPostByUser(Integer userId);
	
	// search Post
	List<PostDto> searchPost(String keyword);
	
	

}
