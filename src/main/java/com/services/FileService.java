package com.services;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
	
	String uploadImage(String path, MultipartFile file) throws Exception;
	
	InputStream getResource(String path, String fileName) throws FileNotFoundException;

}
