package com.bergamota.jasperreport.resources.dtos;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.bergamota.jasperreport.entities.Category;
import com.bergamota.jasperreport.entities.Report;
import com.bergamota.jasperreport.entities.ReportParameter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class ReportDTO implements DataTransformObject<Report>{

	private Long categoryId;
	private Long parentId;
	private String filePath;
	private String name;
	private Long id;
	private String fileName;
	@Builder.Default
	private Set<ReportParameterDTO> parameters = new HashSet<ReportParameterDTO>();
	private ReportConnectionDTO connection;
	@Builder.Default
	private Set<ReportDTO> subReports = new HashSet<ReportDTO>();
	public ReportDTO(Long id, Long categoryId, String filePath, String name, Set<ReportParameter> params, String fileName) {
		this.id = id;
		this.categoryId = categoryId;
		this.filePath = filePath;
		this.name = name;
		this.fileName = fileName;
		this.parameters = params.stream().map(p -> p.transform()).collect(Collectors.toSet());
	}
	
	@Override
	public Report transform() {
		var params = parameters.stream().map(p -> p.transform()).collect(Collectors.toSet());
		var subreports = subReports.stream().map(p -> p.transform()).collect(Collectors.toSet());
		return Report.builder()
				.category(Category.builder().id(categoryId).build())
				.filePath(filePath)
				.name(name)
				.id(id)
				.parent(parentId != null ? Report.builder().id(parentId).build() : null)
				.connection(connection.transform())
				.fileName(fileName)
				.parameters(params)
				.subReports(subreports)
				.build();
	}
		

}
