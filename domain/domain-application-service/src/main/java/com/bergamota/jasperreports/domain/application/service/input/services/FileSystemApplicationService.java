package com.bergamota.jasperreports.domain.application.service.input.services;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileSystemApplicationService {

	void copy(String fullPath, MultipartFile file) throws IOException;
	
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
