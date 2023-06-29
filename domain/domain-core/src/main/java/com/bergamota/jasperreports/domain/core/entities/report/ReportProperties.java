package com.bergamota.jasperreports.domain.core.entities.report;

import lombok.Data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReportProperties {
	
	
	private String reportFileName;
    private List<GenerateReportParameter> reportParams = new ArrayList<>();
    private boolean useDatasourceAndConnection = false;
    private boolean deleteAfterDownload;
    private String folderReportsName;
    private Connection reportConnection;
    private List<?> data;
    private List<ReportPropertiesSubReport> subReports = new ArrayList<ReportPropertiesSubReport>();
	
}
