package com.bergamota.jasperreport.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bergamota.jasperreport.entities.ReportConnection;
import com.bergamota.jasperreport.services.ReportConnectionService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value = "Report Connection Resource", produces = "application/json")
@RestController
@RequestMapping("/report-connections")
@RequiredArgsConstructor
public class ReportConnectionResource {

	private final ReportConnectionService service;
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<ReportConnection> insert(@RequestBody ReportConnection reportConnection) {
		var created = service.save(reportConnection);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ReportConnection> insert(@PathVariable Long id, @RequestBody ReportConnection reportConnection) {
		reportConnection.setId(id);
		var created = service.save(id, reportConnection);
		return ResponseEntity.status(HttpStatus.OK).body(created);
	}
	
	@GetMapping(value="/{name}",produces = "application/json")
	public ResponseEntity<ReportConnection> getByName(@PathVariable String name) {
		var finded = service.findByName(name);
		return ResponseEntity.ok(finded);
	}
	
	@DeleteMapping(value="/{name}",produces = "application/json")
	public ResponseEntity<Void> deleteByName(@PathVariable String name) {
		service.deleteByName(name);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<ReportConnection>> getAll() {
		var allReportConnections = service.findAllWithDecryptedUsername();
		return ResponseEntity.ok(allReportConnections);
	}
	
}
