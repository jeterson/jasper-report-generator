package com.bergamota.jasperreports.dataaccess.report.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "report_config")
public class ReportConfigEntity {
	
	@Id
	private String key;
	private String imagePath;	
	private String generatedReportBasePath;
	private String basePath;	
	private boolean active;


}
