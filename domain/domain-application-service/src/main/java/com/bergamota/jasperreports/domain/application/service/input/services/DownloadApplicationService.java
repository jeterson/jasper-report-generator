package com.bergamota.jasperreports.domain.application.service.input.services;

import jakarta.servlet.http.HttpServletResponse;

public interface DownloadApplicationService {

	void download(HttpServletResponse response, String path, boolean deleteFile, String contentType);
	void download(HttpServletResponse response, String path);
	void download(HttpServletResponse response, String path, boolean deleteFile);
}
