package com.bergamota.jasperreport.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;import com.bergamota.jasperreport.utils.ReportDatabase;

import io.swagger.annotations.Api;

@Api(value = "Database Resource", produces = "application/json")
@RestController
@RequestMapping("/databases")
@CrossOrigin(origins = "http://localhost:4200")
public class DatabaseResource {

	@GetMapping(value = "/supported", produces = {"application/json"})
	public ResponseEntity<List<String>> getSupportedDatabses(@RequestParam(name = "filter", defaultValue = "") String filter) {
		
		var filtered = Arrays.asList(ReportDatabase.values())
				.stream()
				.map(d -> d.name())
				.filter(d -> d.toLowerCase().contains(filter.toLowerCase()))
				.collect(Collectors.toList());
		return ResponseEntity.ok(filtered);
	}
	
	@GetMapping(value = "/supported/{name}", produces = {"application/json"})
	public ResponseEntity<List<String>> getSupportedDatabse(@PathVariable String name) {
		var database = getSupportedDatabses(name).getBody();		
		return ResponseEntity.ok(database.stream().filter(s -> s.toLowerCase().equals(name.toLowerCase())).collect(Collectors.toList()));
	}
}
