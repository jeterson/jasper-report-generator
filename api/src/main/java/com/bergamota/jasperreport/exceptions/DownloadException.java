package com.bergamota.jasperreport.exceptions;

import com.bergamota.jasperreport.exceptions.http.InternalServerErrorException;

public class DownloadException extends InternalServerErrorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DownloadException(String msg) {
		super(msg);
	}
	
	public DownloadException(String msg, Throwable e) {
		super(msg, e);
	}

	
}
