package com.bergamota.jasperreports.domain.application.service.dto.generatereport;

import com.bergamota.jasperreports.domain.core.entities.report.GenerateReportParameter;

import java.util.List;

public record GenerateReportCommand(Long reportId, List<GenerateReportParameter> parameters) {
}
