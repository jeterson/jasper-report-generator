package com.bergamota.jasperreport.model;

import com.bergamota.jasperreport.utils.ReportParamType;

import lombok.Data;

@Data
public class ReportParameter{

	private String name;
	private Object value;
	private ReportParamType type;
	private ReportParamType reportType;
	private String datePattern;
	
}
