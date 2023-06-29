package com.bergamota.jasperreports.domain.application.service.dto.report;

import lombok.Getter;

public record CreateReportCommand(String name, Long categoryId, Long connectionId) {
}
