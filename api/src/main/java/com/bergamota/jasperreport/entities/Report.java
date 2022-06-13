package com.bergamota.jasperreport.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.io.FilenameUtils;

import com.bergamota.jasperreport.resources.dtos.DataTransformObject;
import com.bergamota.jasperreport.resources.dtos.ReportDTO;

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
public class Report implements DataTransformObject<ReportDTO>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String filePath;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;	
	
	@ManyToOne
	@JoinColumn(name="connection_id", referencedColumnName = "id")
	private ReportConnection connection;
	
	@Column(unique = true)
	private String fileName;
	
	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Builder.Default
	private Set<ReportParameter> parameters = new HashSet<ReportParameter>();
	
	@OneToMany(mappedBy = "parent", fetch=FetchType.EAGER)	
	@Builder.Default
	private Set<Report> subReports = new HashSet<Report>();
	
	@ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="report_parent_id")
    private Report parent;
	
	public String getFileName() {		
		if(this.fileName == null)
			this.fileName = FilenameUtils.getBaseName(filePath);
		
		return this.fileName;
	}
	
	public void setFileName(String filename) {
		this.fileName = filename;
	}


	@Override
	public ReportDTO transform() {
		return ReportDTO.builder()
				.categoryId(category != null ? category.getId() : null)
				.fileName(getFileName())
				.filePath(filePath)
				.id(id)
				.name(name)
				.connection(connection.transform())
				.parameters(parameters.stream().map(p -> p.transform()).collect(Collectors.toSet()))
				.subReports(subReports.stream().map(p -> p.transform()).collect(Collectors.toSet()))
				.parentId(parent != null ? parent.getId() : null)
				.build();
	}


	
	
}
