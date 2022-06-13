package com.bergamota.jasperreport.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import com.bergamota.jasperreport.resources.dtos.DataTransformObject;
import com.bergamota.jasperreport.resources.dtos.ReportParameterDTO;
import com.bergamota.jasperreport.utils.ReportParamType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class ReportParameter implements DataTransformObject<ReportParameterDTO> {
	
	
	@Id
	@GeneratedValue(generator="sqlite_report_parameter")
	@TableGenerator(name="sqlite_report_parameter", table="jsr_sequence",
	    pkColumnName="id", valueColumnName="seq",
	    pkColumnValue="report_parameter",
	    initialValue=1, allocationSize=1)
	private Long id;
	private String name;	
	@Enumerated(EnumType.STRING)
	private ReportParamType type;
	@Enumerated(EnumType.STRING)
	private ReportParamType reportType;
	private String pattern;
	private int sortOrder;
	private String label;
	private String defaultValue;		
	private boolean required;	
	private String options;
	private boolean multiSelect;
	private String gridSystemConfig;
	private String dividerTitle;
	private boolean visible;
	
	@ManyToOne
	@JoinColumn(name = "report_id", referencedColumnName = "id")
	private Report report;

	@Override
	public ReportParameterDTO transform() {
		return ReportParameterDTO.builder()
				.pattern(pattern)
				.label(label)
				.order(sortOrder)
				.defaultValue(defaultValue)
				.id(id)
				.dividerTitle(dividerTitle)
				.visible(visible)
				.gridSystemConfig(gridSystemConfig)
				.multiSelect(multiSelect)
				.options(options)
				.required(required)
				.name(name)
				.reportId(report.getId())
				.reportType(multiSelect ? ReportParamType.ARRAY : type == ReportParamType.BOOLEAN ? ReportParamType.STRING : reportType)
				.type(type)
				.build();
	}
}
