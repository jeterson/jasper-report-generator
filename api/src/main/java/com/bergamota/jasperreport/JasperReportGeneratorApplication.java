package com.bergamota.jasperreport;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bergamota.jasperreport.model.ReportParameter;
import com.bergamota.jasperreport.services.JasperReportGeneratorService;
import com.bergamota.jasperreport.services.ReportService;

@SpringBootApplication
public class JasperReportGeneratorApplication implements CommandLineRunner {

	@Autowired
	private JasperReportGeneratorService reportService;
	
	@Autowired
	private ReportService service;
	
	/*@Autowired
	private ReportConnectionService reportConnectionService;
	*/
	
	public static void main(String[] args) {
		SpringApplication.run(JasperReportGeneratorApplication.class, args);
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		var rpt = service.findById(20L);
		
		reportService.generateReport(rpt, Arrays.asList());
	}

}
