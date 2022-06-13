package com.bergamota.jasperreport.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bergamota.jasperreport.resources.dtos.CategoryDTO;
import com.bergamota.jasperreport.resources.dtos.CategoryTreeDTO;
import com.bergamota.jasperreport.services.CategoryService;

@RequestMapping("/categories")
@RestController
@CrossOrigin("http://localhost:4200")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<Void> insert(@RequestBody CategoryDTO category) {
		var c = category.toCategory();
		service.save(c);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping(value="/{id}", produces = "application/json")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CategoryDTO category) {
		var c = category.toCategory();
		service.save(id, c);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(value="/tree", produces = "application/json")
	public ResponseEntity<List<CategoryTreeDTO>> getAsTree() {
		var trees = service.getCategoriesAsTree();		
		return ResponseEntity.ok(trees);
	}
	

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<CategoryDTO>> getAll(@RequestParam(name = "filter", defaultValue = "") String filter) {
		var items = service.findAll(filter).stream().map(CategoryDTO::of).collect(Collectors.toList());		
		return ResponseEntity.ok(items);
	}
	
	@GetMapping(value="/{id}", produces = "application/json")
	public ResponseEntity<CategoryDTO> getById(@PathVariable Long id ) {
		var c = service.findById(id);
		return ResponseEntity.ok(CategoryDTO.of(c));
	}
	
	@DeleteMapping(value="/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteById(@PathVariable Long id ) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
