package com.bergamota.jasperreports.domain.core.entities;

import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportParameter {

    @With
    private Long id;
    private String name;
    private ReportParamType type;
    private ReportParamType reportType;
    private String pattern;
    private Object defaultValue;
    private ReportParameterView reportParameterView;
    @With
    private Report report;
    private  boolean createdManually;

    public int getPosition(){
        return reportParameterView == null ? 0 : reportParameterView.getSortOrder();
    }


}
