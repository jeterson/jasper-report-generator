package com.bergamota.jasperreports.domain.core.entities;

import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
public class ReportParameter {

    private Long id;
    private String name;
    private ReportParamType type;
    private ReportParamType reportType;
    private String pattern;
    private Object defaultValue;
    private ReportParameterView reportParameterView;
    private Report report;

}
