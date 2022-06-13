package com.bergamota.jasperreport.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ReportConfig {
	
	@Id	
	private String key;
	private String imagePath;	
	private String generatedReportBasePath;
	private String basePath;	
	private boolean active;

}
