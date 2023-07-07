package com.bergamota.jasperreports.domain.application.service.dto.report;

public record UpdateReportCommand(String name, Long categoryId, Long connectionId, Long reportId) {
}
