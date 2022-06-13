package com.bergamota.jasperreport.exceptions;

import com.bergamota.jasperreport.exceptions.http.NotFoundException;

public class EntityNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(Class<?> entity, Object...params) {		
		super(String.format("Entity %s not found. %s", entity.getSimpleName(), crateMessage(params)));		
	}
}
