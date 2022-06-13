package com.bergamota.jasperreport.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.exceptions.http.BadRequestException;
import com.bergamota.jasperreport.model.ReportParameter;
import com.bergamota.jasperreport.model.ReportPayload;
import com.bergamota.jasperreport.model.ReportProperties;
import com.bergamota.jasperreport.services.JasperReportGeneratorService;
import com.bergamota.jasperreport.services.ReportConnectionService;
import com.bergamota.jasperreport.services.base.interfaces.ConnectionService;
import com.bergamota.jasperreport.services.base.interfaces.DownloadService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigService;
import com.bergamota.jasperreport.services.base.interfaces.ReportGeneratorService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ReportGeneratorServiceImpl implements ReportGeneratorService {



	
	
	private final ReportConnectionService reportConnectionService;
	
	private final JasperReportGeneratorService generatorService;
	
	private final ConnectionService connectionService;
	
	private final DownloadService downloadService;
	
	private final ReportConfigService configService;
	
	


	@Override
	public String generateReport(ReportPayload payload, HttpServletResponse response) {

		validation(payload);
		var properties = getProperties(payload);									
		String dir = generatorService.generateReport(properties);
		downloadService.download(response, dir, payload.getDeleteAfterDownload());
		return dir;
				
	}
	
	protected void validation(ReportPayload payload) {	
		if(payload.getFileName() == null || payload.getFileName().isBlank())
			throw new BadRequestException("Name of report not provided");
		if(payload.getReportsFolderName() == null || payload.getReportsFolderName().isBlank())
			throw new BadRequestException("Report folder name not provided");
		if(payload.getData() == null && (payload.getConnectionKey() == null || payload.getConnectionKey().isBlank()))
			throw new BadRequestException("Report datasource not provided");
	}

	protected ReportProperties getProperties(ReportPayload payload) {
		var reportProperties = new ReportProperties();
		var reportConnection = reportConnectionService.findByName(payload.getConnectionKey());	
		
		if(payload.getData() == null && reportConnection == null)
			throw new BadRequestException("None datasource provided", null);
		
		var connection = connectionService.getConnection(reportConnection);
		reportProperties.setReportConnection(connection);
		reportProperties.setDeleteAfterDownload(payload.getDeleteAfterDownload());
		reportProperties.setReportFileName(payload.getFileName());
		reportProperties.setFolderReportsName(payload.getReportsFolderName());
		reportProperties.setReportParams(payload.getParameters());
		reportProperties.setUseDatasourceAndConnection(payload.isUseDatasourceAndConnection());
		reportProperties.setData(payload.getData());
		
		return reportProperties;
	}

	@Override
	public String generateReport(Report report, List<ReportParameter> parameters, HttpServletResponse response) {
		
		var dir = generatorService.generateReport(report, parameters);
		downloadService.download(response, dir, configService.deleteAfterDownload());
		return dir;
	}

		

}
