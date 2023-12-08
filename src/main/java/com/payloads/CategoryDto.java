package com.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 3,message = "Title should not be less than 3 chars")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10,message = "Description should not be less than 10 chars")
	private String categoryDescription;

}
