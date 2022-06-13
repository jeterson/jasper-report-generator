package com.bergamota.jasperreport.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bergamota.jasperreport.resources.dtos.DataTransformObject;
import com.bergamota.jasperreport.resources.dtos.ReportConnectionDTO;
import com.bergamota.jasperreport.utils.ReportDatabase;

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
public class ReportConnection implements DataTransformObject<ReportConnectionDTO>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String connectionUrl;
	private String username;
	private String password;
	@Column(unique = true)
	private String name;

	@Enumerated(EnumType.STRING)
	private ReportDatabase database;

	@Override
	public ReportConnectionDTO transform() {
		return ReportConnectionDTO.builder()
				.database(database)
				.id(id)
				.name(name)
				.url(connectionUrl)				
				.build();
	}
}
