package com.bergamota.jasperreports.domain.core.entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReportParameterView {
    private Long id;
    private String label;
    private int sortOrder;
    private boolean visible;
    private boolean required;
}
