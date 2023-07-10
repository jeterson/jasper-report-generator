package com.bergamota.jasperreports.domain.core.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.util.*;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	private List<ReportParameter> parameters = new ArrayList<>();
	private Set<Report> subReports = new HashSet<Report>();
    private Report parent;
	@Setter
	@Getter
	private boolean reportAvailable;

	public String getFullFilePath() {
		return filePath + System.getProperty("file.separator") + fileName;
	}

	public boolean isNotSubReport(){
		return parent == null;
	}
	public  boolean hasSubReport(){
		return subReports.size() > 0;
	}

	public List<ReportParameter> getParameters() {
		if(parameters != null) {
			parameters = new ArrayList<>(parameters.stream().sorted(Comparator.comparingInt(ReportParameter::getPosition)).toList());
		}
		return parameters;
	}



}
