package com.bergamota.jasperreport.resources.dtos;

import com.bergamota.jasperreport.entities.ReportConnection;
import com.bergamota.jasperreport.utils.ReportDatabase;

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
public class ReportConnectionDTO implements DataTransformObject<ReportConnection> {

	private Long id;
	private String name;
	private String url;
	private ReportDatabase database;
	private String password;
	private String username;
	
	
	@Override
	public ReportConnection transform() {
		return ReportConnection.builder()
				.connectionUrl(url)
				.name(name)
				.database(database)
				.id(id)		
				.password(password)
				.username(username)
				.build();
	}
}
