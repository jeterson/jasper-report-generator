package com.bergamota.jasperreports.domain.core.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

	private Long id;
	private String description;
	@Setter
	private String path;
	@With
	private Category parent;

	@Setter
	@Getter
	private List<Report> reports = new ArrayList<>();

	public boolean hasParent() {
		return parent != null;
	}

}
