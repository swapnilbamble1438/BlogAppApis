package com.services;

import com.payloads.CommentDto;
import com.payloads.PostDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
	
	PostDto deleteComment(Integer commentId);
}
