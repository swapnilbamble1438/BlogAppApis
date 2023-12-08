package com.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		//File Name
		String name = file.getOriginalFilename();
		
		
		// random file name generating
		String randomID = UUID.randomUUID().toString();
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));

		//use this when you dont want to change the image file name
	//	String filePath = path+File.separator+fileName1;

		
		//Fullpath
		String filePath = path+File.separator+fileName1;
		
		
		//create folder if not created
		File f = new File(path);
		if(!f.exists())
		{
			f.mkdir();  // make directory or folder
		}
		
		// file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		// File.seperator is used for Slash
		String fullPath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		
		// db logic to return inputstream
		
		return is;
	}
}
