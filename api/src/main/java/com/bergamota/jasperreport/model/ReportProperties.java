package com.bergamota.jasperreport.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ReportProperties {
	
	
	private String reportFileName;
    private List<ReportParameter> reportParams = new ArrayList<>();
    private boolean useDatasourceAndConnection = false;
    private boolean deleteAfterDownload;
    private String folderReportsName;
    private Connection reportConnection;
    private List<?> data;
    private List<ReportPropertiesSubReport> subReports = new ArrayList<ReportPropertiesSubReport>();
	
}
