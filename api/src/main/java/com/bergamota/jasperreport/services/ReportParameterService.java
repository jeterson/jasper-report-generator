package com.bergamota.jasperreport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bergamota.jasperreport.entities.ReportParameter;
import com.bergamota.jasperreport.repositories.ReportParameterRepository;
import com.bergamota.jasperreport.services.base.BaseService;

@Service

public class ReportParameterService extends BaseService<ReportParameter, Long, ReportParameterRepository>{

	
	@Autowired
	public ReportParameterService(ReportParameterRepository repository) {
		super(repository);
	}

	@Override
	public ReportParameter save(Long id, ReportParameter entity) {
		entity.setId(id);
		return save(entity);
	}
	
}
