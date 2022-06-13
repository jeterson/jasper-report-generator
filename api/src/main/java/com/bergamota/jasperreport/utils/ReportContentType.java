package com.bergamota.jasperreport.utils;

public enum ReportContentType {

	APPLICATION_JSON("application/json"),
	APPLICATION_X_MS_DOWNLOAD("application/x-msdownload");
	
	private String value;
	
	private ReportContentType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
