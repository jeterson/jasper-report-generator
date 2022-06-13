package com.bergamota.jasperreport.services.impl;

import java.util.List;

import com.bergamota.jasperreport.entities.ReportConfig;
import com.bergamota.jasperreport.exceptions.ReportConfigException;
import com.bergamota.jasperreport.repositories.ReportConfigRepository;
import com.bergamota.jasperreport.services.base.interfaces.FileSystemService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigActionsService;
import com.bergamota.jasperreport.services.base.interfaces.ReportConfigService;
import com.bergamota.jasperreport.utils.ReportParamsUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ReportConfigServiceImpl implements ReportConfigService, ReportConfigActionsService{
	
	private final ReportConfigRepository repository;
	private final FileSystemService fileSystemService;
	
	private static final String REPORT_CONFIG_DEFAULT_KEY = "default";
	private static final String REPORT_CONFIG_DEFAULT_GENERATED = "generated";	
	private static final String REPORT_CONFIG_DEFAULT_IMAGES = "images";
	
	private ReportConfig config;
	
	public ReportConfigServiceImpl crateDefaultParameter() {
		ReportConfig config = new ReportConfig();
		config.setKey(REPORT_CONFIG_DEFAULT_KEY);
		config.setBasePath(ReportParamsUtil.userHome + ReportParamsUtil.separator);
		config.setGeneratedReportBasePath(REPORT_CONFIG_DEFAULT_GENERATED);
		config.setImagePath(REPORT_CONFIG_DEFAULT_IMAGES);
		config.setActive(true);
		return crateDefaultParameter(config);	
	}	
	

	public ReportConfigServiceImpl crateDefaultParameter(ReportConfig config) {
		if(repository.findById(config.getKey()).isPresent())
			return this;
				
		config = repository.save(config);
		createDirectories(config);
		
		return this;				
	}
	
	private void createDirectories(ReportConfig config) {
		fileSystemService.createDirectoryIfNotExists(config.getBasePath());		
	}
	
	@Override
	public void save(ReportConfig config) {
		this.config = repository.save(config);
		if(config.isActive())
			setToActive(this.config);
		
		createDirectories(config);
	}
	
	@Override
	public List<ReportConfig> getAll() {
		return repository.findAll();
	}

	
	@Override
	public void deleteById(String id) {
		if(repository.findAll().size() == 1)
			throw new ReportConfigException("Exists only one configuration. Deletion not permitted");
		
		var c = repository.findById(id).orElse(null);
		if(c != null) {
			if(c.isActive()) {
				var newActiveConfig = repository.findAll().stream().findFirst().get();
				setToActive(newActiveConfig);
			}
			repository.delete(c);				
		}
		
	}
	
	private void setToActive(ReportConfig config) {
		repository.findAll().forEach(c -> {
			c.setActive(false);
			repository.save(c);
		});
		
		config.setActive(true);
		repository.save(config);
	}
	
	@Override
	public String getBasePath() {		
		return getConfig().getBasePath();
	}

	@Override
	public String getGeneratedReportsBasePath() {		
		return getConfig().getGeneratedReportBasePath();
	}

	@Override
	public String getImagesPath() {
		return getConfig().getImagePath();
	}
	
	@Override
	public ReportConfig getConfig() {
		if(config == null) {
			log.info("Retrieving Config from database");
			var c = repository.findAll().stream().filter(x -> x.isActive()).findFirst().orElse(null);
			if(c==null)
				throw new ReportConfigException("None default configuration found");
			else
				return c;				
		}else {
			log.info("Config is in a cache");
			return config;
		}
		
	}

}
