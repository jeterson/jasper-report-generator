package com.bergamota.jasperreport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bergamota.jasperreport.entities.EmailTaskDefinition;
import com.bergamota.jasperreport.repositories.EmailTaskDefinitionRepository;
import com.bergamota.jasperreport.services.base.BaseService;

@Service
public class EmailTaskDefinitionService extends BaseService<EmailTaskDefinition, Long, EmailTaskDefinitionRepository>{

	@Autowired
	public EmailTaskDefinitionService(EmailTaskDefinitionRepository repository) {
		super(repository);		
	}

	@Override
	public EmailTaskDefinition save(Long id, EmailTaskDefinition entity) {
		entity.setId(id);
		return save(entity);
	}

}
