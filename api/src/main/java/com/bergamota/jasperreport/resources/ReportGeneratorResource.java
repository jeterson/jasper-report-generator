package com.bergamota.jasperreport.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bergamota.jasperreport.model.ReportPayload;
import com.bergamota.jasperreport.services.base.interfaces.ReportGeneratorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Report Generator Resource", produces = "application/json")
@RestController
@RequestMapping("/generator-reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportGeneratorResource {

	@Autowired
	private ReportGeneratorService service;
	
	@ApiOperation(value = "Generate Report")		
	@PostMapping(produces = {"application/json"})	
	public ResponseEntity<Void> generateReport(@RequestBody ReportPayload payload, HttpServletResponse response){
		service.generateReport(payload, response);
		return ResponseEntity.ok().build();
	}
		
}
