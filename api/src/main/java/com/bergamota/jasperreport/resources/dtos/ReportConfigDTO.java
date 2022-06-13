package com.bergamota.jasperreport.resources.dtos;

import com.bergamota.jasperreport.entities.ReportConfig;
import com.bergamota.jasperreport.utils.ReportParamsUtil;

import lombok.Data;

@Data
public class ReportConfigDTO {
	private String key;
	private String imagePath;	
	private String generatedReportBasePath;
	private String basePath;	
	private boolean active;
	private boolean useUserHomeBasePath;
	
	public ReportConfig toReportConfig() {
		var c = new ReportConfig();
		c.setActive(active);
		c.setBasePath(useUserHomeBasePath ? ReportParamsUtil.userHome : basePath);
		c.setGeneratedReportBasePath(generatedReportBasePath);
		c.setImagePath(imagePath);
		c.setKey(key);		
		return c;
	}
}
