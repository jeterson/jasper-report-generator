package com.bergamota.jasperreports.domain.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Setter
public class CategoryTree {

    private Long id;
    private String label;
    @JsonIgnore
    private CategoryTree parent;
    @Builder.Default
    private List<CategoryTree> subItems = new ArrayList<>();

    public static CategoryTree of(Category category) {
        if (category == null)
            return null;

        var dto = CategoryTree.builder()
                .id(category.getId())
                .label(category.getDescription())
                .build();

        dto.setParent(of(category.getParent()));
        return dto;
    }
}


