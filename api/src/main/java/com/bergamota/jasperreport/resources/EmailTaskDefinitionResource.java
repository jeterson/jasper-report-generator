package com.bergamota.jasperreport.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bergamota.jasperreport.entities.EmailTaskDefinition;
import com.bergamota.jasperreport.resources.dtos.EmailTaskDefinitionDTO;
import com.bergamota.jasperreport.services.EmailTaskDefinitionService;

@RestController
@RequestMapping("/email-tasks")
public class EmailTaskDefinitionResource extends BaseResource<EmailTaskDefinition, Long, EmailTaskDefinitionDTO,EmailTaskDefinitionService>{

	@Autowired
	public EmailTaskDefinitionResource(EmailTaskDefinitionService service) {
		super(service);
	}

}
