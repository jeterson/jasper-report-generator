package com.bergamota.jasperreports.common.domain.valueobjects;

public enum ReportDatabase {

	SQLITE("org.sqlite.JDBC"),
	ORACLE("oracle.jdbc.driver.OracleDriver")
	;
	
	
	private final String driver;
	
	public String getDriver() {
		return driver;
	}
	
	private ReportDatabase(String driver) {
		this.driver = driver;
	}
}
