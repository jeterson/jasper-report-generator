package com.bergamota.jasperreports.domain.application.service.common;

import com.bergamota.jasperreports.domain.core.entities.ReportConfig;

public interface ReportGeneratorConfig {

    ReportConfig findDefault();

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
