package com.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entites.Category;
import com.exceptions.ResourceNotFoundException;
import com.payloads.CategoryDto;
import com.repositories.CategoryRepo;
import com.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = DtoToCategory(categoryDto);
		Category savedCategory = categoryRepo.save(category);
		
		return CategoryToDto(savedCategory);
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));
		
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedcategory = categoryRepo.save(category);
		CategoryDto updatedcategoryDto = CategoryToDto(updatedcategory);
		
		return updatedcategoryDto;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		 Category category = categoryRepo.findById(categoryId)
				 .orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
		 categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {

		 Category category = categoryRepo.findById(categoryId)
				 .orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
		
		 return CategoryToDto(category);
	}

	@Override
	public List<CategoryDto> getCategories() {

		List<Category> categories = categoryRepo.findAll();
		
		List<CategoryDto> categoriesDto = categories.stream().map(category->CategoryToDto(category)).collect(Collectors.toList());
			
		return categoriesDto;
	}
	
	
	public Category DtoToCategory(CategoryDto categoryDto)
	{
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	public CategoryDto CategoryToDto(Category category)
	{
		CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
