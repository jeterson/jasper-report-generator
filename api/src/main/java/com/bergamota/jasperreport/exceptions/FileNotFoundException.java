package com.bergamota.jasperreport.exceptions;

import com.bergamota.jasperreport.exceptions.http.NotFoundException;

public class FileNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public FileNotFoundException(String fileName, Object...params) {		
		super(String.format("File %s not found. %s", fileName, crateMessage(params)));		
	}
	
}
