package com.bergamota.jasperreport.services.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bergamota.jasperreport.exceptions.EntityNotFoundException;

public abstract class BaseService<T, ID, R extends JpaRepository<T, ID>> extends GetTypeParent<T> {

	protected R repository;
	
	public BaseService(R repository) {
		this.repository = repository;
	}
	
	public abstract T save(ID id, T entity);		
	
	public T save(T entity) {
		return repository.save(entity);
	}
	
	public T findById(ID id) {
		return repository.findById(id).orElse(null);
	}
	public T findByIdThowNotFound(ID id) {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(getGenericClass(), "id", id));
	}
	public void delete(ID id) {
		repository.deleteById(id);
	}
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}


		
}
