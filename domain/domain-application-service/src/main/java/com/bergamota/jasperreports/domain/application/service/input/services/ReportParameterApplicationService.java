package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.application.service.dto.reportparameter.CreateReportParameterCommand;
import com.bergamota.jasperreports.domain.application.service.dto.reportparameter.UpdateReportParameterCommand;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;

public interface ReportParameterApplicationService {

    ReportParameter create(CreateReportParameterCommand reportParameter);
    ReportParameter update(Long id, UpdateReportParameterCommand reportParameter);

    boolean createFromXml(Long reportId);
    void remove(Long id);
}
