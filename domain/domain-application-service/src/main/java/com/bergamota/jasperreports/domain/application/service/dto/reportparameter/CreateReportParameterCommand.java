package com.bergamota.jasperreports.domain.application.service.dto.reportparameter;

public record CreateReportParameterCommand(Long reportId, String defaultValue, String name, String pattern, String reportType, String type) {
}
