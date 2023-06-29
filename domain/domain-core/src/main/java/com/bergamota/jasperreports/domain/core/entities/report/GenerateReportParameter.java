package com.bergamota.jasperreports.domain.core.entities.report;


import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenerateReportParameter {

	private String name;
	private Object value;
	private ReportParamType type;
	private ReportParamType reportType;
	private String datePattern;
	
}
