package com.bergamota.jasperreport.utils;

public enum ReportDatabase {

	SQLITE("org.sqlite.JDBC"),
	ORACLE("oracle.jdbc.driver.OracleDriver")
	;
	
	
	private String driver;
	
	public String getDriver() {
		return driver;
	}
	
	private ReportDatabase(String driver) {
		this.driver = driver;
	}
}
