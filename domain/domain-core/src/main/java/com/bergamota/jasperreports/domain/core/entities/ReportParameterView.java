package com.bergamota.jasperreports.domain.core.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportParameterView {
    private Long id;
    private String label;
    private int sortOrder;
    private boolean visible;
    private boolean required;
    @With
    private ReportParameter reportParameter;
}
