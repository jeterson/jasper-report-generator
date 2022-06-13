package com.bergamota.jasperreport.services.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.bergamota.jasperreport.components.ConversionTypesComponent;
import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.exceptions.ReportParameterException;
import com.bergamota.jasperreport.model.ReportParameter;
import com.bergamota.jasperreport.model.ReportProperties;
import com.bergamota.jasperreport.model.ReportPropertiesSubReport;
import com.bergamota.jasperreport.services.base.interfaces.ConnectionService;
import com.bergamota.jasperreport.services.base.interfaces.FileSystemService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigService;
import com.bergamota.jasperreport.utils.ReportParamType;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseSubreport;

public abstract class BaseJasperReportGeneratorService {

	private ConversionTypesComponent converter;
	private ReportConfigService configService;
	private ConnectionService connectionService;
	private FileSystemService fileSystemService;
	
	protected Map<String, Object> params = new HashMap<>();
	
	
	public BaseJasperReportGeneratorService(ConnectionService connectionService, ReportConfigService configService,ConversionTypesComponent converter, FileSystemService fileSystemService) {
		this.converter = converter;
		this.configService = configService;
		this.connectionService = connectionService;
		this.fileSystemService = fileSystemService;
	}
	
	
	protected void fillReportParamsMap(List<ReportParameter> parameters) {

		var params = new HashMap<String, Object>();

		parameters.forEach(p -> {
			var entrySet = convertToEntryMap(p);
			params.put(entrySet.getKey(), entrySet.getValue());
		});
		
		fillReportSystemParams();

	}
	
	protected ReportProperties getReportProperties(Report report) {
		var properties = new ReportProperties();
		var connection = connectionService.getConnection(report.getConnection());
		
		properties.setReportConnection(connection);
		properties.setDeleteAfterDownload(true);
		properties.setReportFileName(fileSystemService.getFileName(report.getFileName()));
		properties.setFolderReportsName(report.getFilePath().replace(configService.getBasePath(), ""));		
		properties.setUseDatasourceAndConnection(true);
		properties.setData(null);
		report.getSubReports().forEach(subrpt -> {
			properties.getSubReports().add(new ReportPropertiesSubReport(subrpt.getFilePath(), subrpt.getFileName()));
		});
		
		return properties;
	}
	
	protected List<ReportPropertiesSubReport> fillSubreports(JasperReport jasperReport, ReportProperties prop) {
		
		// if already exists subReports in list, use it.
		if(prop.getSubReports().size() > 0)
			return prop.getSubReports();
		
		List<ReportPropertiesSubReport> subreports = new ArrayList<ReportPropertiesSubReport>();
		for(JRBand band: jasperReport.getAllBands()) {
			var subreportsOfBand = band.getChildren()
					.stream()
					.filter(c -> c instanceof JRBaseSubreport)
					.collect(Collectors.toList());
			
			for(JRChild child: subreportsOfBand) {
				JRBaseSubreport subRpt = (JRBaseSubreport) child;
				var chunk = Arrays.asList(subRpt.getExpression().getChunks()).stream().findFirst().orElse(null);
				if(chunk != null) {
					String fileNameChunk = chunk.getText();
					subreports.add(new ReportPropertiesSubReport(prop.getFolderReportsName(), fileNameChunk));					
				}
			}
		}
		prop.setSubReports(subreports);
		return subreports;
	}

	
	private void fillReportSystemParams() {
		params.put("context", configService.getBasePath());
		params.put("imagesPath", configService.getImagesPath());		
		params.put("os", System.getProperty("os.name"));
		params.put("user", System.getProperty("user.name"));
		params.put("lang", System.getProperty("user.language"));		
	}
	
	private Entry<String, Object> convertToEntryMap(ReportParameter parameter){
		var value = parameter.getValue();
		var name = parameter.getName();
		var reportType = parameter.getReportType();
		var type = parameter.getType();
		String pattern = parameter.getDatePattern();
		
		switch(type){
			case DATE:
				return handleWhenTypeIsDate(value, name, type, reportType, pattern);
			case INTEGER:
				return createEntrySet(name, converter.parseInteger(value).getValue());
			case DECIMAL:
				return createEntrySet(name, converter.parseDobule(value).getValue());
			case BOOLEAN:
				var cr = converter.parseToYesNo(value);
				return createEntrySet(name, cr.getValue().value());
			case ARRAY:
				return createEntrySet(name, converter.toCollection(value));
		default:
			return createEntrySet(name, converter.parseString(value));
		}
		
	}
	private Entry<String, Object> handleWhenTypeIsDate(Object value, String name, ReportParamType type, ReportParamType reportType, String pattern) {
		if(pattern == null || pattern.isBlank()) 
			throw new ReportParameterException(String.format("Parameter %s is a dateType, but not contain a pattern of date", name));
		
		if(reportType == ReportParamType.DATE) {
			var cr = converter.parseDateUtil(value, pattern);
			return createEntrySet(name, cr.getValue());
		}else {
			var cr = converter.parseDateUtil(value, pattern);
			if(cr.isOk())
				return createEntrySet(name, converter.parseString(value));
			else
				return createEntrySet(name, null);
		}
			
	}
	
	private Entry<String, Object> createEntrySet(String key, Object value) {
		return new Entry<String, Object>() {


			@Override
			public Object setValue(Object value) {
				// TODO Auto-generated method stub
				return value;
			}

			@Override
			public Object getValue() {
				// TODO Auto-generated method stub
				return value;
			}

			@Override
			public String getKey() {
				// TODO Auto-generated method stub
				return key;
			}
		};
	}
		
}
