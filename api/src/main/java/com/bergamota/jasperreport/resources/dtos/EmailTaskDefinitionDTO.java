package com.bergamota.jasperreport.resources.dtos;

import com.bergamota.jasperreport.entities.EmailTaskDefinition;

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
public class EmailTaskDefinitionDTO implements DataTransformObject<EmailTaskDefinition>{

	private Long id;
	private String cronExpression;	
	private Long referenceId;
	private String name;
	private String subject;
	private String[] recipients;		
	private String body;
	
	@Override
	public EmailTaskDefinition transform() {	
		return EmailTaskDefinition.builder()
				.body(body)
				.cronExpression(cronExpression)
				.id(id)
				.name(name)
				.recipients(recipients)
				.subject(subject)
				.referenceId(referenceId)
				.build();
	}

}
