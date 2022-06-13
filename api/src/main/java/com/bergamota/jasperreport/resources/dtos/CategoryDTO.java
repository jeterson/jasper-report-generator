package com.bergamota.jasperreport.resources.dtos;

import com.bergamota.jasperreport.entities.Category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class CategoryDTO {

	private Long id;
	private String description;	
	private CategoryDTO parent;
	private String path;
		
	
	public Category toCategory() {
		return Category
				.builder()
				.id(id)
				.description(description)				
				.parent(parent == null ? null : Category.builder().id(parent != null ? parent.getId() : null).build())
				.build();
	}
	
	public static CategoryDTO of(Category c) { 
		if(c != null) {
			var dto = CategoryDTO.builder()
					.id(c.getId())
					.description(c.getDescription())							
					.build();
			
			dto.setParent(of(c.getParent()));
			dto.setPath(c.getPath());	
			return dto;

		}

		return null;
	}
	
	@SuppressWarnings("unused")
	private static String categoryPath(CategoryDTO dto) {
		String path = "";
		dto.setPath(dto.getPath() == null ? "" : dto.getPath());
		
		if(dto.getParent() == null) {
			path = "/" + dto.getDescription();
		}else {
			path = dto.getParent().getPath() + "/" + dto.getDescription();
		}
		
		return path;
	}
}
