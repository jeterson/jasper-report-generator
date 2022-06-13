package com.bergamota.jasperreport.services.impl.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bergamota.jasperreport.exceptions.FileNotFoundException;
import com.bergamota.jasperreport.services.base.interfaces.FileSystemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemServiceImpl implements FileSystemService{

	@Override
	public long copy(String fullPath, MultipartFile file) throws IOException {
		Path root = Paths.get(fullPath);
		if(!Files.exists(root))
			createDirectoryIfNotExists(fullPath);
		
		return Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
	}

	@Override
	public void createDirectoryIfNotExists(String path) {
		log.info("Creating directory path " + path);
        File file = new File(path);
        if (!file.exists()) {
            log.info(file.mkdirs() ? "Directory was created" : "Directory not created");
        } else {
            log.info("Directory already exists");
        }
		
	}

	@Override
	public String getFileName(String fullPath) {
		return FilenameUtils.getBaseName(fullPath);
	}

	@Override
	public File createFile(String path) {
		File file = new File(path);
		if(!file.exists()) {
			log.error("File not exists {}", path);
			throw new FileNotFoundException(file.getPath());
		}
		return file;
	}

}
