package com.bergamota.jasperreport.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.bergamota.jasperreport.exceptions.DownloadException;
import com.bergamota.jasperreport.services.base.interfaces.DownloadService;
import com.bergamota.jasperreport.utils.ReportContentType;

public class DownloadServiceImpl implements DownloadService{

	@Override
	public void download(HttpServletResponse response, String path, boolean deleteFile, ReportContentType contentType) {
		 File file = new File(path);
	        try (InputStream is = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
	            response.reset();
	            response.setContentType(contentType.getValue());
	            response.setContentLength((int) file.length());
	            response.addHeader("Access-Control-Allow-Origin", "*");
	            response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
	            IOUtils.copy(is, out);	            
	            out.flush();	            
	        } catch (IOException e) {
	            throw new DownloadException("Error when trying download file " + path, e);
	        } finally {
	            if (deleteFile)
	                file.delete();

	        }

		
	}

	@Override
	public void download(HttpServletResponse response, String path) {
		download(response, path, true, ReportContentType.APPLICATION_X_MS_DOWNLOAD);
		
	}

	@Override
	public void download(HttpServletResponse response, String path, boolean deleteFile) {
		download(response, path, deleteFile, ReportContentType.APPLICATION_X_MS_DOWNLOAD);
		
	}

}
