package com.bergamota.jasperreports.domain.application.service.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import com.bergamota.jasperreports.domain.application.service.input.services.DownloadApplicationService;
import com.bergamota.jasperreports.domain.core.exceptions.DownloadDomainException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;


@Service
public class DownloadApplicationServiceImpl implements DownloadApplicationService {

	@Override
	public void download(HttpServletResponse response, String path, boolean deleteFile, String contentType) {
		 File file = new File(path);
	        try (InputStream is = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
	            response.reset();
	            response.setContentType(contentType);
	            response.setContentLength((int) file.length());
	            response.addHeader("Access-Control-Allow-Origin", "*");
	            response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
	            IOUtils.copy(is, out);	            
	            out.flush();	            
	        } catch (IOException e) {
	            throw new DownloadDomainException("Error when trying download file " + path, e);
	        } finally {
	            if (deleteFile)
	                file.delete();

	        }

		
	}

	@Override
	public void download(HttpServletResponse response, String path) {
		download(response, path, true, "application/x-msdownload");
		
	}

	@Override
	public void download(HttpServletResponse response, String path, boolean deleteFile) {
		download(response, path, deleteFile, "application/x-msdownload");
		
	}

}
