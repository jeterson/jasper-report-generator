package com.bergamota.jasperreport.exceptions.http;

import org.springframework.http.HttpStatus;

import com.bergamota.jasperreport.exceptions.base.BaseHttpException;

public class NotFoundException extends BaseHttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException(String msg, Throwable e) {
		super(HttpStatus.NOT_FOUND, msg, e);
	}
	
	public NotFoundException(String msg) {
		super(HttpStatus.NOT_FOUND, msg);
	}
}
