package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.application.service.common.ReportGeneratorConfig;
import com.bergamota.jasperreports.domain.core.entities.Report;
import com.bergamota.jasperreports.domain.core.entities.report.GenerateReportParameter;
import com.bergamota.jasperreports.domain.core.entities.report.ReportProperties;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface JasperReportGeneratorApplicationService {
    String generateReport(ReportProperties properties);
    String generateReport(Report report, List<GenerateReportParameter> parameters);
    String generateReport(ReportProperties reportProperties, HttpServletResponse response);
}
