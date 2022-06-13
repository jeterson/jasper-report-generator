package com.bergamota.jasperreport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import com.bergamota.jasperreport.repositories.ReportConfigRepository;
import com.bergamota.jasperreport.services.JasperReportGeneratorService;
import com.bergamota.jasperreport.services.ReportConnectionService;
import com.bergamota.jasperreport.services.base.interfaces.ConnectionService;
import com.bergamota.jasperreport.services.base.interfaces.DownloadService;
import com.bergamota.jasperreport.services.base.interfaces.ExportPDF;
import com.bergamota.jasperreport.services.base.interfaces.FileSystemService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigActionsService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigService;
import com.bergamota.jasperreport.services.base.interfaces.ReportGeneratorService;
import com.bergamota.jasperreport.services.impl.ConnectionServiceImpl;
import com.bergamota.jasperreport.services.impl.DownloadServiceImpl;
import com.bergamota.jasperreport.services.impl.ExportPDFImpl;
import com.bergamota.jasperreport.services.impl.ReportConfigServiceImpl;
import com.bergamota.jasperreport.services.impl.ReportGeneratorServiceImpl;
import com.bergamota.jasperreport.services.impl.filesystem.FileSystemServiceImpl;

@Configuration
public class GeneratorConfigFactory {
		
	
	@Bean
	@DependsOn(value = {"reportConfig", "fileSystemService"})
	public ExportPDF exportPDF(final ReportConfigService configService, FileSystemService fileSystemService) {
		return new ExportPDFImpl(configService, fileSystemService);
	}
	@Bean
	public DownloadService downloadService() {
		return new DownloadServiceImpl();
	}
	
	@Bean
	public FileSystemService fileSystemService() {
		return new FileSystemServiceImpl();
	}
	
	@Bean
	public ConnectionService connectionService() {
		return new ConnectionServiceImpl();
	}
	
	@Bean
	@DependsOn(value = {"reportConfig", "connectionService", "downloadService"})
	public ReportGeneratorService reportGeneratorService(final ConnectionService connectionService, 
														 final ReportConnectionService reportConnectionService, 
														 final JasperReportGeneratorService generatorService, 
														 final DownloadService downloadService,
														 final ReportConfigService configService) {
		return new ReportGeneratorServiceImpl(reportConnectionService, generatorService, connectionService, downloadService,configService);
	}
	
	@Bean	
	@Primary
	@DependsOn("fileSystemService")
	public ReportConfigService reportConfig(final ReportConfigRepository repository, final FileSystemService fileSystemService) {
		return new ReportConfigServiceImpl(repository, fileSystemService)
				.crateDefaultParameter();
	}
	
	@Bean	
	@DependsOn("fileSystemService")
	public ReportConfigActionsService reportConfigActions(final ReportConfigRepository repository, final FileSystemService fileSystemService) {
		return new ReportConfigServiceImpl(repository, fileSystemService)
				.crateDefaultParameter();
	}
	
		
	

}
