package com.bergamota.jasperreport.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bergamota.jasperreport.entities.ReportConfig;
import com.bergamota.jasperreport.resources.dtos.ReportConfigDTO;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigActionsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/report-configs")
@RequiredArgsConstructor
public class ReportConfigResource {
	
	private final ReportConfigActionsService service;
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<Void> createConfig(@RequestBody ReportConfigDTO config) {
		service.save(config.transform());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<ReportConfig>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@DeleteMapping(value="/{id}", produces = "application/json")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
