package com.bergamota.jasperreport.exceptions.base;

import org.springframework.http.HttpStatus;

public abstract class BaseHttpException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int status;	

	public BaseHttpException(HttpStatus status, String msg, Throwable e) {
		super(msg, e);
		this.status = status.value();
	}
	
	public BaseHttpException(int status, String msg, Throwable e) {
		super(msg, e);
		this.status = status;
	}
	
	public BaseHttpException(HttpStatus status,String msg) {
		super(msg);
		this.status =status.value();
	}
	
	public int getStatus() {
		return status;
	}	

}
