package com.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.config.AppConstants;
import com.entites.Post;
import com.exceptions.ApiException;
import com.payloads.ApiResponse;
import com.payloads.PostDto;
import com.payloads.PostResponse;
import com.services.FileService;
import com.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	// create
	@PostMapping("/{userId}/{categoryId}") // in this url we are putting userId and categoryId also
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			)
	{
		System.out.println("==============================");
		PostDto createPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}

	
	// get Posts by User
	@GetMapping("/u/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		
		List<PostDto> postDtos = postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		
	}
	
	// get Posts by Category
		@GetMapping("/c/{categoryId}")
		public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
			
			List<PostDto> postDtos = postService.getPostByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
			
		}
		
		// get all posts
		/*
		@GetMapping("/getall")
		public ResponseEntity<List<PostDto>> getAllPost()
		{
			List<PostDto> postDtos =	postService.getAllPost();
			return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		}
		*/
		
		/*
		 while sending url of get all post by using pagination
		 select Get All Posts
		 and in below url there will be Params options
		 in that, in Key type "pageNo" and in Value type "1" or Number of page you want
		 and again in key type "pageSize" and in Value type "2" or Size of page you want
		 */
		
		// get posts by using pagination
		/*
		@GetMapping("/getall")
		public ResponseEntity<List<PostDto>> getAllPost(@RequestParam(value = "pageNo",defaultValue="0",required = false) Integer pageNo,
				@RequestParam(value = "pageSize",defaultValue = "2", required = false) Integer pageSize)
		{
			List<PostDto> postDtos =	postService.getAllPost(pageNo,pageSize);
			return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		}
		*/
		
		// get posts by using pagination2
		/*
				@GetMapping("/getall")
				public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo",defaultValue="0",required = false) Integer pageNo,
						@RequestParam(value = "pageSize",defaultValue = "2", required = false) Integer pageSize)
				{
					PostResponse postResp =	postService.getAllPost(pageNo,pageSize);
					return new ResponseEntity<PostResponse>(postResp,HttpStatus.OK);
				}
				*/
		/*
		 while sending url of get all post by using pagination
		 select Get All Posts
		 and in below url there will be Params options
		 in that, in Key type "pageNo" and in Value type "1" or Number of page you want
		 and again in key type "pageSize" and in Value type "2" or Size of page you want
		 and again in key type "sortBy" and in Value type e.g "postId" or "title" 
		 or "content" according to which you update.
		 */
		// get posts by using pagination3   // added sortBy 
		/*
		@GetMapping("/getall")
		public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo",defaultValue="0",required = false) Integer pageNo,
				@RequestParam(value = "pageSize",defaultValue = "2", required = false) Integer pageSize,
				@RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy)				
		{
			PostResponse postResp =	postService.getAllPost(pageNo,pageSize,sortBy);
			return new ResponseEntity<PostResponse>(postResp,HttpStatus.OK);
		}
		*/
		
		// without using AppConstants
		// get posts by using pagination4   //  added sortDir (Sorting Direction)
		/*		
		@GetMapping("/getall")
				public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo",defaultValue="0",required = false) Integer pageNo,
						@RequestParam(value = "pageSize",defaultValue = "2", required = false) Integer pageSize,
						@RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
						@RequestParam(value = "sortDir",defaultValue ="asc", required = false)String sortDir)
				{
					PostResponse postResp =	postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
					return new ResponseEntity<PostResponse>(postResp,HttpStatus.OK);
				}
			*/
		//using AppConstants
		// get posts by using pagination4   //  added sortDir (Sorting Direction)
		@GetMapping("/getall")
		public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo",defaultValue=AppConstants.PAGE_NUMBER,required = false) Integer pageNo,
				@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
				@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
				@RequestParam(value = "sortDir",defaultValue =AppConstants.SORT_DIR, required = false)String sortDir)
		{
			PostResponse postResp =	postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
			return new ResponseEntity<PostResponse>(postResp,HttpStatus.OK);
		}
	
		
		
		
		
		// get Post by Id
		@GetMapping("/{postId}")
		public ResponseEntity<PostDto> getSinglePost(@PathVariable("postId") Integer postId)
		{
			return ResponseEntity.ok(postService.getPostById(postId));
		}
	
		
		// delete post by id
		@DeleteMapping("/delete/{postId}")
		public ResponseEntity<List<PostDto>> deletePost(@PathVariable Integer postId)
		{
			List<PostDto> postDtos = postService.deletePost(postId);
			return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		}
		
		// update Post
		@PutMapping("/{postId}/{categoryId}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId,@PathVariable Integer categoryId)
		{
			PostDto updatePost = postService.updatePost(postDto, postId,categoryId);

			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		}
		
	// search
		@GetMapping("/search/{keyword}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword)
		{
			List<PostDto> postDtos = postService.searchPost(keyword);

			return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		}
		
		
	
		
		
		// post image upload / and image update
				@PutMapping("/uploadimage/{postId}")
				public ResponseEntity<PostDto> updatePostImage(
						@RequestParam("image") MultipartFile image,
						@PathVariable("postId") Integer postId) throws Exception
				{
					PostDto postDto = postService.getPostById(postId);

					String fileName = fileService.uploadImage(path, image);
					
					Path filepath = Paths.get(path + File.separator + postDto.getImageName());
					
					if(Files.exists(filepath))
					{
						try {
							
							Files.delete(filepath);
							
						} catch (IOException e) {
							
							throw new ApiException("Failed to delete the Post Image");
							
						}
					}
					
					
					postDto.setImageName(fileName);
					
					PostDto updatedPostDto = postService.updatePost(postDto, postId,postDto.getCategory().getCategoryId());
					
					return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
					
				
				
				}
		
		
		
		
		// method to serve, view or read image file
		@GetMapping(value="/image/{postId}",produces= MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("postId") Integer postId,
				HttpServletResponse response) throws IOException
		{
			PostDto postDto = postService.getPostById(postId);
			String imageName = postDto.getImageName();
			System.out.println(imageName);
				
				String ImagePath = path;
				if(imageName.equals("default.jpg"))
				{
					File f = new ClassPathResource("static/image").getFile();
						ImagePath =	f.getAbsolutePath();
				}
		InputStream resource =fileService.getResource(ImagePath, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
		}
	
		
		
	
}
