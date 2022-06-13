package com.bergamota.jasperreport.services.base.interfaces;

public interface ReportConfigService {

	String getBasePath();

    String getGeneratedReportsBasePath();

    String getImagesPath();    

    default boolean deleteAfterDownload() {
        return true;
    }

    default String getTypeFileOutput() {
        return ".pdf";
    }

    default String getTypeFileInput() {
        return ".jrxml";
    }

    default String generatedJasperFileName() {
        return "report.jasper";
    }

}
