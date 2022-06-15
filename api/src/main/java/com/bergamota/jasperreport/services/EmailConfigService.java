package com.bergamota.jasperreport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bergamota.jasperreport.entities.EmailConfig;
import com.bergamota.jasperreport.exceptions.http.NotFoundException;
import com.bergamota.jasperreport.repositories.EmailConfigRepository;
import com.bergamota.jasperreport.services.base.BaseService;

@Service
public class EmailConfigService extends BaseService<EmailConfig, Long, EmailConfigRepository>{

	@Autowired
	public EmailConfigService(EmailConfigRepository repository) {
		super(repository);		
	}

	@Override
	public EmailConfig save(Long id, EmailConfig entity) {
		entity.setId(id);
		return save(entity);
	}
	
	@Override
	public EmailConfig save(EmailConfig entity) {		
		var saved = super.save(entity);
		if(saved.isActive()) {
			setEmailActive(saved);
			JavaMailerService.getInstance().setEmailConfig(saved);
		}		
		return saved;
	}
	
	private void setEmailActive(EmailConfig config) {
		repository.findAll().forEach(c -> {
			c.setActive(false);
		});
		repository.flush();
		repository.save(config);
	}
	
	public EmailConfig getDefault() {
		return repository.findAll().stream().filter(e -> e.isActive()).findFirst().orElseThrow(() -> new NotFoundException("None default config for email found"));
	}

}
