package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.core.entities.ReportParameter;

import java.util.Set;

public interface ReportExtractorParameterApplicationService {
    Set<ReportParameter> extractParametersFromJrXml(String xmlPath);
}
