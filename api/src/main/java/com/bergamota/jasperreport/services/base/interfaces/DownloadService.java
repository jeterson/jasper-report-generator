package com.bergamota.jasperreport.services.base.interfaces;

import javax.servlet.http.HttpServletResponse;

import com.bergamota.jasperreport.utils.ReportContentType;

public interface DownloadService {

	void download(HttpServletResponse response, String path, boolean deleteFile, ReportContentType contentType);
	void download(HttpServletResponse response, String path);
	void download(HttpServletResponse response, String path, boolean deleteFile);
}
