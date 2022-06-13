package com.bergamota.jasperreport.exceptions;

import org.springframework.http.HttpStatus;

import com.bergamota.jasperreport.exceptions.base.BaseHttpException;

public class ConnectionException extends BaseHttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectionException(HttpStatus status, String msg, Throwable e) {
		super(status, msg, e);		
	}
	
	public ConnectionException(String msg, Throwable e) {
		this(HttpStatus.BAD_REQUEST, msg, e);		
	}
	
	public ConnectionException(String msg) {
		this(HttpStatus.BAD_REQUEST, msg, null);		
	}
	
	public ConnectionException(HttpStatus status, String msg) {
		this(status, msg, null);		
	}

	
	
}
