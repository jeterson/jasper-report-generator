package com.bergamota.jasperreports.domain.application.service.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import com.bergamota.jasperreports.domain.application.service.input.services.FileSystemApplicationService;
import com.bergamota.jasperreports.domain.core.exceptions.FileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileSystemApplicationServiceImpl implements FileSystemApplicationService {

	@Override
	public void copy(String fullPath, MultipartFile file) throws IOException {
		Path root = Paths.get(fullPath);
		if(!Files.exists(root))
			createDirectoryIfNotExists(fullPath);
		
		Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(file.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
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

	@Override
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void delete(String fileName) {
		var file = createFile(fileName);
		
		if(file.exists()) {
			file.delete();
		} else
			log.warn("File {} not exists", fileName);
		
	}

	@Override
	public boolean exists(String filePath) {
		try {
			var file = createFile(filePath);
			return file.exists();
		}catch (Exception ex){
			log.warn("File exists check failed: {}", ex.getMessage());
			return false;
		}
	}

}
