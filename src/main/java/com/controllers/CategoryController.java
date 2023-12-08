package com.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payloads.ApiResponse;
import com.payloads.CategoryDto;
import com.payloads.UserDto;
import com.services.CategoryService;
import com.services.UserService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/create")   //@Valid is used for activating working of validator
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategoryDto = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer categoryId)
	{
		CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
		
		return ResponseEntity.ok(updatedCategory);	
	}
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryid)
	{
		categoryService.deleteCategory(categoryid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
	
	}
	
	@GetMapping("/getall")
	public  ResponseEntity<List<CategoryDto>> getCategories()
	{
		
		return ResponseEntity.ok(categoryService.getCategories());
	}
	
	@GetMapping("/{categoryId}")
	public  ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("categoryId") Integer categoryId)
	{
		
		return ResponseEntity.ok(categoryService.getCategory(categoryId));
	}
	
	

}
