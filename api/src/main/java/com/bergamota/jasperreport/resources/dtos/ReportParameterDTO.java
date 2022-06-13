package com.bergamota.jasperreport.resources.dtos;

import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.entities.ReportParameter;
import com.bergamota.jasperreport.utils.ReportParamType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class ReportParameterDTO implements DataTransformObject<ReportParameter> {

	private String pattern;
	private Long reportId;
	private String name;
	private ReportParamType type;
	private ReportParamType reportType;
	private Long id;
	private int order;
	private String label;
	private String defaultValue;
	private boolean required;	
	private String options;
	private boolean multiSelect;
	private String gridSystemConfig;
	private String dividerTitle;	
	private boolean visible;
	
	@Override
	public ReportParameter transform() {		
		return ReportParameter.builder()
				.pattern(pattern)
				.options(options)
				.label(label)
				.sortOrder(order)
				.defaultValue(defaultValue)
				.name(name)
				.visible(visible)
				.dividerTitle(dividerTitle)
				.gridSystemConfig(gridSystemConfig)
				.multiSelect(multiSelect)
				.required(required)
				.reportType(multiSelect ? ReportParamType.ARRAY : type == ReportParamType.BOOLEAN ? ReportParamType.STRING : reportType)
				.type(type)
				.id(id)
				.report(reportId  == null ? null : Report.builder().id(reportId).build())
				.build();
	}

}
