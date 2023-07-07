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

    @JsonIgnore
    private Long id;
    private String label;
    @JsonIgnore
    private boolean isReport;
    @JsonIgnore
    private List<Report> reports;
    @JsonIgnore
    private CategoryTree parent;
    public String getCode(){
        return String.format("%s-%s", getHeader(), id);
    }

    public String getHeader() {
        return isReport ? "report" : "category";
    }
    @Builder.Default
    private List<CategoryTree> subItems = new ArrayList<>();

    public static CategoryTree of(Category category) {
        if (category == null)
            return null;

        var dto = CategoryTree.builder()
                .id(category.getId())
                .label(category.getDescription())
                .reports(category.getReports())
                .build();

        dto.setParent(of(category.getParent()));
        return dto;
    }
}


