package com.bergamota.jasperreport.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bergamota.jasperreport.entities.EmailConfig;
import com.bergamota.jasperreport.resources.dtos.EmailConfigDTO;
import com.bergamota.jasperreport.services.EmailConfigService;

@RestController
@RequestMapping("/email-config")
public class EmailConfigResource extends BaseResource<EmailConfig, Long, EmailConfigDTO, EmailConfigService> {

	@Autowired
	public EmailConfigResource(EmailConfigService service) {
		super(service);		
	}

	
}
