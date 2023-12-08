package com.payloads;

import com.entites.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
	
	
	private int id;
	
	private String content;
	
	private UserDto user;


}
