package com.bergamota.jasperreports.domain.core.entities;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
public class Category {

	private Long id;
	private String description;
	@Setter
	private String path;
	private Category parent;

}
