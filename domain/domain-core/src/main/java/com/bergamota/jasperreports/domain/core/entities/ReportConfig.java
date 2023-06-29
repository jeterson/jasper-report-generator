package com.bergamota.jasperreports.domain.core.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@With
@Builder
@Getter
public class ReportConfig {

	private String key;
	private String imagePath;	
	private String generatedReportBasePath;
	private String basePath;	
	private boolean active;
	private List<ReportConfig> configurations = new ArrayList<>();

	public void makeAsDefault(){
		configurations.forEach(e -> e.active = false);
		this.active = true;
	}
	public Optional<ReportConfig> electOtherConfigToActive(){
		if(configurations.stream().findFirst().isEmpty())
			return Optional.empty();

		var newConfig = configurations.stream().findFirst();
		configurations.forEach(e -> e.active = false);
		newConfig.get().active = true;
		return newConfig;
	}

}
