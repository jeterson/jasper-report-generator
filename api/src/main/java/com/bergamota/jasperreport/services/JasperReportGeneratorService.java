package com.bergamota.jasperreport.services;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bergamota.jasperreport.components.ConversionTypesComponent;
import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.exceptions.FileNotFoundException;
import com.bergamota.jasperreport.exceptions.JasperReportGeneratorException;
import com.bergamota.jasperreport.model.ReportParameter;
import com.bergamota.jasperreport.model.ReportProperties;
import com.bergamota.jasperreport.model.ReportPropertiesSubReport;
import com.bergamota.jasperreport.services.base.BaseJasperReportGeneratorService;
import com.bergamota.jasperreport.services.base.interfaces.ConnectionService;
import com.bergamota.jasperreport.services.base.interfaces.DownloadService;
import com.bergamota.jasperreport.services.base.interfaces.ExportPDF;
import com.bergamota.jasperreport.services.base.interfaces.FileSystemService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigService;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;

@Service
@Slf4j
public class JasperReportGeneratorService extends BaseJasperReportGeneratorService{

	private final ReportConfigService config;	
	private final DownloadService downloadService;	
	private final FileSystemService fileSystemService;	
	private final ExportPDF exportPDF;
	
	
	@Autowired
	public JasperReportGeneratorService(ReportConfigService configService,
										DownloadService downloadService,
										FileSystemService fileSystemService,
										ExportPDF exportPDF,
										ConversionTypesComponent converter,
										ConnectionService connectionService) {
		super(connectionService, configService, converter,fileSystemService);
		this.config = configService;
		this.downloadService = downloadService;
		this.fileSystemService = fileSystemService;
		this.exportPDF = exportPDF;
		

				
	}

	private String getPathFile(ReportProperties p) {
		var path = config.getBasePath() + fileSystemService.separator()
									    + p.getFolderReportsName() 
									    + fileSystemService.separator() 
									    + p.getReportFileName() 
									    + config.getTypeFileInput();		
		return path;
	}
	
	private String getCompiledPath(ReportProperties p) {
		var path = config.getBasePath() 
							+ fileSystemService.separator()  
							+ p.getFolderReportsName() 
							+ fileSystemService.separator()  
							+ config.getGeneratedReportsBasePath();		
		return path;
	}
	
	private String getCompiledFileName(String compiledPath) {
		return compiledPath + fileSystemService.separator()
							+ System.currentTimeMillis() 
							+ config.generatedJasperFileName();
	}
	
	
    private List<String> compileSubreport(ReportProperties prop) throws JRException, java.io.FileNotFoundException {    
    	var subreportCompiledFiles = new ArrayList<String>();
    	for(ReportPropertiesSubReport subrpt: prop.getSubReports()) {    		
    		
    		var fileName = subrpt.getFileName();
    		var fullName = subrpt.getFilePath() + fileSystemService.separator() + fileName;
    		var filePath = subrpt.getFilePath();
    		var paramName = fileSystemService.getFileName(fileName);
    		
    		log.info("Compiling subreport {}", fullName);
    		var file = fileSystemService.createFile(fullName);
    		
    		if(!file.exists())
    			throw new FileNotFoundException(fullName);
    		
    		var compiled = JasperCompileManager.compileReport(new FileInputStream(file));    		
    		log.info("SubReport compiled");
    		var compiledFileName = filePath + fileSystemService.separator() 
    										+ config.getGeneratedReportsBasePath()
    										+ fileSystemService.separator()
    										+fileName+System.currentTimeMillis()+".jasper";
    		
    		log.info("saving SubReport {}", compiledFileName);
    		JRSaver.saveObject(compiled, compiledFileName);
    		log.info("Saved.");
    		params.put(paramName, compiledFileName);
    		log.info("Param " + paramName + " added in report");
    		subreportCompiledFiles.add(compiledFileName);
    		
    	}
    	return subreportCompiledFiles;
    	
    }            
   
	public String generateReport(ReportProperties properties) {
        return generateReport(properties, null);
    }
	
	public String generateReport(Report report, List<ReportParameter> parameters) {
		var properties = getReportProperties(report);
		properties.setReportParams(parameters);
		return generateReport(properties);
	}
    
    @SneakyThrows
	public String generateReport(ReportProperties reportProperties, HttpServletResponse response) {
		Connection connection = reportProperties.getReportConnection();
		List<String> subreportCompiledFiles = null;
		String compiledFileName = "";
		var data = reportProperties.getData();
		
		try {
			log.info("Generating report {}", reportProperties.getReportFileName());
			fillReportParamsMap(reportProperties.getReportParams());
			
			var pathFile = getPathFile(reportProperties);
			var compiledPath = getCompiledPath(reportProperties);
			fileSystemService.createDirectoryIfNotExists(compiledPath);
			fileSystemService.createDirectoryIfNotExists(config.getBasePath() + fileSystemService.separator()  + reportProperties.getFolderReportsName());
			var file = fileSystemService.createFile(pathFile);
			var reportStream = new FileInputStream(file);
			log.info("Compiling jasper...");
			var jasperReport = JasperCompileManager.compileReport(reportStream);
			fillSubreports(jasperReport, reportProperties);
			compiledFileName = getCompiledFileName(compiledPath);
			JRSaver.saveObject(jasperReport, compiledFileName);
			log.info("Jasper compiled");			
			subreportCompiledFiles = compileSubreport(reportProperties);
			JasperPrint jasperPrint;
			
			if(data == null) {
				log.info("No datasource is passed. Using external datasource");
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
			}else {
				log.info("Using local datasource. {} items", data.size());
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(data));
			}
			
			String outputFilePath = exportPDF.export(reportProperties, jasperPrint);
			if(response != null) {
				log.info("Put binary on response");
				downloadService.download(response, outputFilePath, reportProperties.isDeleteAfterDownload());
			}else {
				log.info("Report generated");
			}
			return outputFilePath;
			


		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new JasperReportGeneratorException(e.getMessage(), e);
		}finally {
			deleteFiles(subreportCompiledFiles);
			deleteFiles(Arrays.asList(compiledFileName));
			
			if( connection != null && !connection.isClosed())
				connection.close();
				
		}
	}
    
    private void deleteFiles(List<String> files) {
    	if(files == null)
    		return;
    	
    	for(String file : files) {
    		var f = fileSystemService.createFile(file);
    		if(f.exists())
    			f.delete();
    	}
    }


}
