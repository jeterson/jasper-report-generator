package com.bergamota.jasperreport.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bergamota.jasperreport.entities.ReportParameter;
import com.bergamota.jasperreport.resources.dtos.ReportParameterDTO;
import com.bergamota.jasperreport.services.ReportParameterService;

@RestController
@RequestMapping("/report-parameters")
public class ReportParameterResource extends BaseResource<ReportParameter, Long, ReportParameterDTO, ReportParameterService>{

	public ReportParameterResource(ReportParameterService service) {
		super(service);
	}

}
