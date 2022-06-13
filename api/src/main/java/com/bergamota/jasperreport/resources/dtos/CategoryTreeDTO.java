package com.bergamota.jasperreport.resources.dtos;

import java.util.ArrayList;
import java.util.List;

import com.bergamota.jasperreport.entities.Category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryTreeDTO {

	private Long id;
	private String label;	
	private CategoryTreeDTO parent;
	@Builder.Default
	private List<CategoryTreeDTO> subItems = new ArrayList<>();


	public static CategoryTreeDTO of(Category c) { 
		if(c != null) {
			var dto = CategoryTreeDTO.builder()
					.id(c.getId())
					.label(c.getDescription())								
					.build();

			dto.setParent(of(c.getParent()));
			return dto;

		}

		return null;
	}
}
