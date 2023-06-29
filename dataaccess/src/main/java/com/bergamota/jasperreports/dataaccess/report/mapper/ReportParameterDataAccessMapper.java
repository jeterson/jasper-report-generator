package com.bergamota.jasperreports.dataaccess.report.mapper;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportEntity;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportParameterEntity;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportParameterViewEntity;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import com.bergamota.jasperreports.domain.core.entities.ReportParameterView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ReportParameterDataAccessMapper {

    private final ReportDataAccessMapper reportDataAccessMapper;

    public ReportParameter domainEntity(ReportParameterEntity entity){
        return domainEntity(entity, true, true);
    }
    public ReportParameter domainEntity(ReportParameterEntity entity, boolean fillParent, boolean fillViews){

        return ReportParameter.builder()
                .defaultValue(entity.getDefaultValue())
                .id(entity.getId())
                .type(entity.getType())
                .name(entity.getName())
                .reportType(entity.getReportType())
                .pattern(entity.getPattern())
                .report(reportDataAccessMapper.domainEntity(entity.getReport(),false,false))
                .reportParameterView(!fillViews ? null : domainEntityView(entity.getReportParameterView()))
                .build();
    }
    public ReportParameterEntity dataAccessEntity(ReportParameter reportParameter){
        return ReportParameterEntity.builder()
                .defaultValue(String.valueOf(reportParameter.getDefaultValue() == null ? "" : reportParameter.getDefaultValue()))
                .id(reportParameter.getId())
                .name(reportParameter.getName())
                .pattern(reportParameter.getPattern())
                .type(reportParameter.getType())
                .reportType(reportParameter.getReportType())
                .reportParameterView(reportParameter.getReportParameterView() == null ? null :ReportParameterViewEntity.builder().id(reportParameter.getReportParameterView().getId()).build())
                .report(reportDataAccessMapper.dataAccessEntity(reportParameter.getReport()))
                .build();
    }
    public ReportParameterView domainEntityView(ReportParameterViewEntity entity){
        if(entity == null)
            return null;
        return ReportParameterView.builder()
                .visible(entity.isVisible())
                .label(entity.getLabel())
                .sortOrder(entity.getSortOrder())
                .required(entity.isRequired())
                .id(entity.getId())
                .build();
    }

    public ReportParameterViewEntity dataAccessEntityView(ReportParameterView reportParameterView){
        if(reportParameterView == null)
            return null;

        return ReportParameterViewEntity.builder()
                .visible(reportParameterView.isVisible())
                .label(reportParameterView.getLabel())
                .sortOrder(reportParameterView.getSortOrder())
                .required(reportParameterView.isRequired())
                .id(reportParameterView.getId())
                .build();
    }

}
