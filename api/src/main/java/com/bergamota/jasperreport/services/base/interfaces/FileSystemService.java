package com.bergamota.jasperreport.services.base.interfaces;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileSystemService {

	long copy(String fullPath, MultipartFile file) throws IOException;
	
	void createDirectoryIfNotExists(String path);
	
	String getFileName(String fullPath);	
	
	File createFile(String path);
	
	void delete(String fileName);
	
	default String separator() {
		return System.getProperty("file.separator");
	}
	
	default String userHomePath() {
		return System.getProperty("user.home");
	}
	
}
