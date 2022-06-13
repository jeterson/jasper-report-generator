package com.bergamota.jasperreport.exceptions.http;

import org.springframework.http.HttpStatus;

import com.bergamota.jasperreport.exceptions.base.BaseHttpException;

public class InternalServerErrorException extends BaseHttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InternalServerErrorException(String msg, Throwable e) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, msg, e);
	}
	
	public InternalServerErrorException(String msg) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, msg);
	}
}
