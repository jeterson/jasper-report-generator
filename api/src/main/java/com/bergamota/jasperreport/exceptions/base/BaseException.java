package com.bergamota.jasperreport.exceptions.base;

public abstract class BaseException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public BaseException(String msg) {
		super(msg);
	}
	
	public static String crateMessage(Object... args) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<args.length;i+=2) {
			sb.append(String.format("%s=%s", args[i], args[i+1]));
		}
		
		return sb.toString();
	}

}
