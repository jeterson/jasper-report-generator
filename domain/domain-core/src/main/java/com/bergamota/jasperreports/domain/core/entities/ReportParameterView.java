package com.bergamota.jasperreports.domain.core.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Builder
@Getter
public class ReportParameterView {
    private Long id;
    private String label;
    private int sortOrder;
    private boolean visible;
    private boolean required;
    @With
    private ReportParameter reportParameter;
}
