package com.bergamota.jasperreport.resources;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bergamota.jasperreport.resources.dtos.DataTransformObject;
import com.bergamota.jasperreport.services.base.BaseService;

public abstract class BaseResource<ENTITY extends DataTransformObject<DTO>, ID, DTO extends DataTransformObject<ENTITY>, SERVICE> {

	protected BaseService<ENTITY, ID, JpaRepository<ENTITY,ID>> service;
	
	@SuppressWarnings("unchecked")
	public BaseResource(SERVICE service) {
		this.service = (BaseService<ENTITY, ID, JpaRepository<ENTITY,ID>>) service ;
	}
	
	@PostMapping(produces = "application/json")
	protected ResponseEntity<DTO> insert(@RequestBody DTO obj) {
		var saved = service.save(obj.transform());		
		return ResponseEntity.status(HttpStatus.CREATED).body(saved.transform());
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	protected ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO dto) {
		var saved = service.save(id, dto.transform());
		return ResponseEntity.ok(saved.transform());
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	protected ResponseEntity<Void> delete(@PathVariable ID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	protected ResponseEntity<DTO> getById(@PathVariable ID id) {
		var obj = service.findByIdThowNotFound(id);
		return ResponseEntity.ok(obj.transform());
	}
	
	@GetMapping(produces = "application/json")
	protected ResponseEntity<Page<DTO>> getPageable(@PageableDefault(size=10) Pageable pageable) {
		var page = service.findAll(pageable);
		var pageDTO = page.map(item -> item.transform());
		return ResponseEntity.ok(pageDTO);
	}

}
