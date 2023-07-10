package com.bergamota.jasperreports.domain.core.entities.report;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReportProperties {
	
	
	private String reportFileName;
    private List<GenerateReportParameter> reportParams = new ArrayList<>();
    private boolean useConnection = false;
    private boolean deleteAfterDownload;
    private String folderReportsName;
    private Long reportConnectionId;
    private List<?> data;
    private List<ReportPropertiesSubReport> subReports = new ArrayList<ReportPropertiesSubReport>();
	
}
