package com.bergamota.jasperreport.exceptions.http;

import org.springframework.http.HttpStatus;

import com.bergamota.jasperreport.exceptions.base.BaseHttpException;

public class BadRequestException extends BaseHttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String msg, Throwable e) {
		super(HttpStatus.BAD_REQUEST, msg, e);
	}
	
	public BadRequestException(String msg) {
		super(HttpStatus.BAD_REQUEST, msg);
	}
}
