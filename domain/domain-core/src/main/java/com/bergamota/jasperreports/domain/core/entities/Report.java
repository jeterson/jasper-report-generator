package com.bergamota.jasperreports.domain.core.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class Report {

	private Long id;
	@With
	private String filePath;
	private String name;
	private Category category;
	private ConnectionConfig connectionConfig;
	@With
	private String fileName;
	@With
	private Set<ReportParameter> parameters = new HashSet<>();
	private Set<Report> subReports = new HashSet<Report>();
    private Report parent;
	@Setter
	@Getter
	private boolean reportAvailable;

	public String getFullFilePath() {
		return filePath + System.getProperty("file.separator") + fileName;
	}

	public boolean isSubReport(){
		return parent != null;
	}
	public  boolean hasSubReport(){
		return subReports.size() > 0;
	}


}
