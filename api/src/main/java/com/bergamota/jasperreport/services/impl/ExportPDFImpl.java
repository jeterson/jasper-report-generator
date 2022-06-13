package com.bergamota.jasperreport.services.impl;

import java.time.LocalDateTime;

import com.bergamota.jasperreport.exceptions.ExportPDFException;
import com.bergamota.jasperreport.model.ReportProperties;
import com.bergamota.jasperreport.services.base.interfaces.ExportPDF;
import com.bergamota.jasperreport.services.base.interfaces.FileSystemService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

@RequiredArgsConstructor
@Slf4j
public class ExportPDFImpl implements ExportPDF {


	private final ReportConfigService configService; 
	private final FileSystemService fileSystemService;


	@Override
	public String export(ReportProperties properties, JasperPrint jasperPrint) {
		JRPdfExporter exporter = new JRPdfExporter();

		var fileName = getExportedFileName(properties.getReportFileName()) + configService.getTypeFileOutput();
		var exportPath = configService.getBasePath() 
				+ fileSystemService.separator() 
				+ properties.getFolderReportsName() 
				+ fileSystemService.separator() 
				+ configService.getGeneratedReportsBasePath() 
				+ fileSystemService.separator() 
				+ properties.getFolderReportsName();
		var exportFileName = exportPath + fileSystemService.separator() + fileName;
		
		fileSystemService.createDirectoryIfNotExists(exportPath);
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportFileName));

		SimplePdfReportConfiguration simplePdfExporterConfiguration = new SimplePdfReportConfiguration();
		simplePdfExporterConfiguration.setForceLineBreakPolicy(false);
		
		SimplePdfExporterConfiguration exporterConfiguration = new SimplePdfExporterConfiguration();
        exporterConfiguration.setMetadataAuthor(System.getProperty("user.name"));

        exporter.setConfiguration(simplePdfExporterConfiguration);
        exporter.setConfiguration(exporterConfiguration);


        try {
        	exporter.exportReport();
        	return exportFileName;
        }catch (Exception e) {
        	log.error("Error when trying export pdf of file " + exportFileName, e);
			throw new ExportPDFException("Error when trying export pdf of file " + exportFileName, e);
		}

	}

	private String getExportedFileName(String filename) {
		var dateNow = LocalDateTime.now();
		return String.format("%s %s-%s-%s-%s-%s-%s", filename, dateNow.getYear(), dateNow.getMonthValue(), dateNow.getDayOfMonth(), dateNow.getHour(), dateNow.getMinute(), dateNow.getSecond());
	}

}
