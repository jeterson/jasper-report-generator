package com.bergamota.jasperreport.exceptions;

import org.springframework.http.HttpStatus;

import com.bergamota.jasperreport.exceptions.base.BaseHttpException;

public class ReportConfigException extends BaseHttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReportConfigException(HttpStatus status, String msg, Throwable e) {
		super(status, msg, e);		
	}
	
	public ReportConfigException(String msg, Throwable e) {
		this(HttpStatus.BAD_REQUEST, msg, e);		
	}
	
	public ReportConfigException(String msg) {
		this(HttpStatus.BAD_REQUEST, msg, null);		
	}
	
	public ReportConfigException(HttpStatus status, String msg) {
		this(status, msg, null);		
	}
}
