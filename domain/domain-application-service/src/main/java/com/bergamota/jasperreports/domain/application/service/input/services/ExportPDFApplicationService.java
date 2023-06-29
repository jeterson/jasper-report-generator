package com.bergamota.jasperreports.domain.application.service.input.services;

import com.bergamota.jasperreports.domain.core.entities.report.ReportProperties;
import net.sf.jasperreports.engine.JasperPrint;

public interface ExportPDFApplicationService {

    String export(ReportProperties properties, JasperPrint jasperPrint);
}
