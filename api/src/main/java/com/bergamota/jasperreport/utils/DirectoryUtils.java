package com.bergamota.jasperreport.utils;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DirectoryUtils {

	public static void createIfNotExists(String path) {
		log.info("Creating directory path " + path);
        File file = new File(path);
        if (!file.exists()) {
            log.info(file.mkdirs() ? "Directory was created" : "Directory not created");
        } else {
            log.info("Directory already exists");
        }
	}
}
