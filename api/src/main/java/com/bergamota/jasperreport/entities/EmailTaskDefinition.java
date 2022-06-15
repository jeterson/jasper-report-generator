package com.bergamota.jasperreport.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;

import com.bergamota.jasperreport.resources.dtos.DataTransformObject;
import com.bergamota.jasperreport.resources.dtos.EmailTaskDefinitionDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class EmailTaskDefinition implements DataTransformObject<EmailTaskDefinitionDTO>{

	@Id	
	@GeneratedValue(generator="sqlite_task_definition")
	@TableGenerator(name="sqlite_task_definition", table="jsr_sequence",
	pkColumnName="id", valueColumnName="seq",
	pkColumnValue="taskDefinition",
	initialValue=1, allocationSize=1)
	private Long id;
	private String cronExpression;	
	private Long referenceId;
	private String name;
	private String subject;
	private String[] recipients;
	
	
	@Lob
	private String body;
	@Override
	public EmailTaskDefinitionDTO transform() {
		return EmailTaskDefinitionDTO.builder()
				.id(id)
				.body(body)
				.cronExpression(cronExpression)
				.referenceId(referenceId)
				.name(name)
				.subject(subject)
				.recipients(recipients)
				.build();
	}	
	
}
