package com.bergamota.jasperreport.services.base.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.model.ReportParameter;
import com.bergamota.jasperreport.model.ReportPayload;

public interface ReportGeneratorService {

	String generateReport(ReportPayload payload, HttpServletResponse response);
	String generateReport(Report report,List<ReportParameter> parameters, HttpServletResponse response);
}
