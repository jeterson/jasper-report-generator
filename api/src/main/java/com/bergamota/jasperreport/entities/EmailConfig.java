package com.bergamota.jasperreport.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import com.bergamota.jasperreport.resources.dtos.DataTransformObject;
import com.bergamota.jasperreport.resources.dtos.EmailConfigDTO;

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
public class EmailConfig implements DataTransformObject<EmailConfigDTO> {

	@Id
	@GeneratedValue(generator="sqlite_mail_config")
	@TableGenerator(name="sqlite_mail_config", table="jsr_sequence",
	pkColumnName="id", valueColumnName="seq",
	pkColumnValue="emailConfig",
	initialValue=1, allocationSize=1)
	private Long id;
	private int port;
	private String host;
	private String user;
	private String password;
	private boolean ttls;
	private boolean auth;
	private boolean active;
	private String sender;
	
	
	@Override
	public EmailConfigDTO transform() {
		return EmailConfigDTO.builder()
				.active(active)
				.id(id)
				.sender(sender)
				.password(password)
				.host(host)
				.port(port)
				.ttls(ttls)
				.auth(auth)
				.user(user)				
				.build();
	}
}
