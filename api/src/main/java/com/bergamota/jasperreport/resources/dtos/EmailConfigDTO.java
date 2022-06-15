package com.bergamota.jasperreport.resources.dtos;

import com.bergamota.jasperreport.entities.EmailConfig;

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
public class EmailConfigDTO implements DataTransformObject<EmailConfig> {

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
	public EmailConfig transform() {
		return EmailConfig.builder()
				.id(id)
				.port(port)
				.host(host)
				.sender(sender)				
				.user(user)
				.password(password)
				.ttls(ttls)
				.auth(auth)
				.active(active)							
				.build();
	}

}
