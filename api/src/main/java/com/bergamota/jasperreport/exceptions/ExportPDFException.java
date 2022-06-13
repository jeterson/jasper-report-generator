package com.bergamota.jasperreport.exceptions;

import com.bergamota.jasperreport.exceptions.http.InternalServerErrorException;

public class ExportPDFException extends InternalServerErrorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExportPDFException(String msg) {
		super(msg);
	}
	
	public ExportPDFException(String msg, Throwable e) {
		super(msg, e);
	}

	
}
