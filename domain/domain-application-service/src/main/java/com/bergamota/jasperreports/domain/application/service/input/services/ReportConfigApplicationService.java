package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.application.service.common.ReportGeneratorConfig;
import com.bergamota.jasperreports.domain.core.entities.ReportConfig;

public interface ReportConfigApplicationService extends ReportGeneratorConfig {

    ReportConfig create(ReportConfig reportConfig);
    ReportConfig update(ReportConfig reportConfig);
    void remove(String s);

    void makeAsDefault(String id);

}
