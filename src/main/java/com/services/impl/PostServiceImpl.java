package com.services.impl;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.entites.Category;
import com.entites.Post;
import com.entites.User;
import com.exceptions.ApiException;
import com.exceptions.ResourceNotFoundException;
import com.payloads.PostDto;
import com.payloads.PostResponse;
import com.repositories.CategoryRepo;
import com.repositories.PostRepo;
import com.repositories.UserRepo;
import com.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Value("${project.image}")
	private String path;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ", "User Id",userId));
				
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(()-> 
				new ResourceNotFoundException("Category ","Category Id",categoryId));
		
		Post post = DtoToPost(postDto);
		
		post.setImageName("default.jpg");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = postRepo.save(post);
		
		return PostToDto(newPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId,Integer categoryId) {

		Post post =	postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		if( postDto.getImageName() == null || postDto.getImageName().equals(""))
		{
		post.setImageName("default.jpg");
		}
		else {
			post.setImageName(postDto.getImageName());
		}
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(()-> 
				new ResourceNotFoundException("Category ","Category Id",categoryId));
		post.setCategory(category);		
		Post updatedpost = postRepo.save(post);
		
		PostDto postDtoUpdated = PostToDto(updatedpost);
		
		return postDtoUpdated;
		
	}

	@Override
	public List<PostDto> deletePost(Integer postId) {

	Post post =	postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
			
		Path filepath = Paths.get(path + File.separator + post.getImageName());
		
		if(Files.exists(filepath))
		{
			try {
				
				Files.delete(filepath);
				
			} catch (IOException e) {
				
				throw new ApiException("Failed to delete the Post Image");
				
			}
		}
		
		post.getImageName();
	
	     postRepo.delete(post);
		
		List<Post> posts = postRepo.findAll();
		
		List<PostDto> postDtos = posts.stream().map((p)-> PostToDto((Post) p)).collect(Collectors.toList());
		
		return postDtos;
	}
	
	// get all Post
	/*
	@Override
	public List<PostDto> getAllPost() {

		List<Post> posts = postRepo.findAll();
		List<PostDto> postDtos = posts.stream().map((post)-> PostToDto((Post) post)).collect(Collectors.toList());

		return postDtos;
	}*/
	
	
	// get posts by using pagination
	/*
	@Override
	public List<PostDto> getAllPost(Integer pageNo,Integer pageSize) {
		 
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		Page<Post> pagePost = postRepo.findAll(pageable);
		 List<Post> posts = pagePost.getContent();
		
		
		List<PostDto> postDtos = posts.stream().map((post)-> PostToDto((Post) post)).collect(Collectors.toList());

		return postDtos;
	}
	*/
	
	// get posts by using pagination2
	/*
		@Override
		public PostResponse getAllPost(Integer pageNo,Integer pageSize) {
			 
			
			Pageable pageable = PageRequest.of(pageNo, pageSize);

			Page<Post> pagePost = postRepo.findAll(pageable);
			 List<Post> posts = pagePost.getContent();
			
			
			List<PostDto> postDtos = posts.stream().map((post)-> PostToDto((Post) post)).collect(Collectors.toList());

			PostResponse postResp = new PostResponse();
			postResp.setContent(postDtos);
			postResp.setPageNo(pagePost.getNumber());
			postResp.setPageSize(pagePost.getSize());
			postResp.setTotalElements(pagePost.getTotalElements());
			postResp.setTotalPages(pagePost.getTotalPages());
			postResp.setLastPage(pagePost.isLast());
			
			return postResp;
		}
		*/
	
	// get posts by using pagination3
	/*
			@Override
			public PostResponse getAllPost(Integer pageNo,Integer pageSize,String sortBy) {
				 
																								//								___				
				Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());//Sort.by(sortBy)                 | You can use any one of this
																								//Sort.by(sortBy).ascending()   ---| by default without putting ascending
				Page<Post> pagePost = postRepo.findAll(pageable);								//Sort.by(sortBy).descending()  ___| or descending it will be ascending
				 List<Post> posts = pagePost.getContent();
				
				
				List<PostDto> postDtos = posts.stream().map((post)-> PostToDto((Post) post)).collect(Collectors.toList());

				PostResponse postResp = new PostResponse();
				postResp.setContent(postDtos);
				postResp.setPageNo(pagePost.getNumber());
				postResp.setPageSize(pagePost.getSize());
				postResp.setTotalElements(pagePost.getTotalElements());
				postResp.setTotalPages(pagePost.getTotalPages());
				postResp.setLastPage(pagePost.isLast());
				
				return postResp;
			}
			*/
	
	// get posts by using pagination4
				@Override
				public PostResponse getAllPost(Integer pageNo,Integer pageSize,String sortBy,String sortDir) {
					 
					// use this
						/*	     
					Sort sort = null;
					if(sortDir.equals("asc"))
					{
						sort = Sort.by(sortBy).ascending();
					}
					else if(sortDir.equals("desc"))
					{
						sort = Sort.by(sortBy).descending();
					}
					else
					{
						sort = Sort.by(sortBy);
					}
					*/
					// or use this both are same, its the ternary operator
					Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():
					 	sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():
					 		Sort.by(sortBy));
																									//								___				
					Pageable pageable = PageRequest.of(pageNo, pageSize,sort);//Sort.by(sortBy)                 | You can use any one of this
																									//Sort.by(sortBy).ascending()   ---| by default without putting ascending
					Page<Post> pagePost = postRepo.findAll(pageable);								//Sort.by(sortBy).descending()  ___| or descending it will be ascending
					 List<Post> posts = pagePost.getContent();
					
					
					List<PostDto> postDtos = posts.stream().map((post)-> PostToDto((Post) post)).collect(Collectors.toList());

					PostResponse postResp = new PostResponse();
					postResp.setContent(postDtos);
					postResp.setPageNo(pagePost.getNumber());
					postResp.setPageSize(pagePost.getSize());
					postResp.setTotalElements(pagePost.getTotalElements());
					postResp.setTotalPages(pagePost.getTotalPages());
					postResp.setLastPage(pagePost.isLast());
					
					return postResp;
				}
		
	
	

	@Override
	public PostDto getPostById(Integer postId) {

 Post post =postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
	
 	PostDto postDto = PostToDto(post);
 	return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException(
						"Category","category id",categoryId));
		
		List<Post> posts = postRepo.findByCategory(category);
		
	List<PostDto> postDtos = posts.stream().map((post)-> PostToDto((Post) post)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		
		User user = userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException(
						"User","user id",userId));
		List<Post> posts = postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map((post)-> PostToDto((Post) post)).collect(Collectors.toList());
		
		return postDtos;
		
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		
	List<Post> posts =	postRepo.findByTitleContaining("%"+ keyword +"%");
	List<PostDto> postDtos = posts.stream().map((post)-> PostToDto((Post) post)).collect(Collectors.toList());

	
		return postDtos;
	}
	
	
	
	
	
	public Post DtoToPost(PostDto postDto)
	{
		Post post = modelMapper.map(postDto, Post.class);
		return post;
	}
	
	public PostDto PostToDto(Post post)
	{
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}

}
