package com.services;

import java.util.List;

import com.payloads.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	void deleteCategory(Integer categoryId);

	 CategoryDto getCategory(Integer categoryId);
	
	List<CategoryDto> getCategories();


}
