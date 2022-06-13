package com.bergamota.jasperreport.resources.dtos;

import com.bergamota.jasperreport.entities.ReportConfig;

import lombok.Data;

@Data
public class ReportConfigDTO implements DataTransformObject<ReportConfig> {
	private String key;
	private String imagePath;	
	private String generatedReportBasePath;
	private String basePath;	
	private boolean active;
	private boolean useUserHomeBasePath;
	
	
	@Override
	public ReportConfig transform() {
		return ReportConfig.builder()
				.active(active)
				.imagePath(imagePath)
				.generatedReportBasePath(generatedReportBasePath)
				.key(key)
				.basePath(useUserHomeBasePath ? System.getProperty("user.home") : basePath)
				.build();
	}
}
