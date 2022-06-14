package com.bergamota.jasperreport.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.model.ReportParameter;
import com.bergamota.jasperreport.resources.dtos.ReportDTO;
import com.bergamota.jasperreport.services.ReportService;
import com.bergamota.jasperreport.services.base.interfaces.ReportGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@RestController
@RequestMapping("/reports")
public class ReportResource extends BaseResource<Report, Long, ReportDTO, ReportService>{

	private ReportGeneratorService generatorService;
	private ReportService _service;
	@Autowired
	public ReportResource(ReportService service, ReportGeneratorService generatorService) {
		super(service);
		this._service = service;
		this.generatorService = generatorService;
	}
	
	@SneakyThrows
	@PostMapping(value="/create-with-upload", produces = "application/json", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Void> insert(@RequestParam("report") String reportDTO, @RequestParam("file") MultipartFile file) {
		  ObjectMapper objectMapper = new ObjectMapper();
		  ReportDTO dto = objectMapper.readValue(reportDTO, ReportDTO.class);
		    
		var report = dto.transform();
		_service.saveWithUpload(report, file);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping(value="/generate/{reportId}", produces = "application/json")
	public ResponseEntity<Void> generateReport(@PathVariable Long reportId, @RequestBody List<ReportParameter> parameters, HttpServletResponse response) {
		var report = service.findById(reportId);				
		generatorService.generateReport(report, parameters, response);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value="/generate", produces = "application/json")
	public ResponseEntity<Void> generateReport(@RequestParam("reportName") String reportName, @RequestBody List<ReportParameter> parameters, HttpServletResponse response) {
		var report = _service.findByName(reportName);		
		generatorService.generateReport(report, parameters, response);
		return ResponseEntity.ok().build();
	}
	
	@Override
	protected ResponseEntity<Page<ReportDTO>> getPageable(Pageable pageable) {
		var responsePage = super.getPageable(pageable);
		var reports = responsePage.getBody().getContent();
		var filtered =reports.stream().filter(r -> r.getParentId() == null).collect(Collectors.toList());
		Page<ReportDTO> newPage = new PageImpl<>(filtered, pageable, 0);
		return ResponseEntity.ok(newPage);
		
		
	}

}
