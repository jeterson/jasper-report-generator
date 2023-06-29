package com.bergamota.jasperreports.dataaccess.report.mapper;

import com.bergamota.jasperreports.dataaccess.category.mapper.CategoryDataAccessMapper;
import com.bergamota.jasperreports.dataaccess.connectionconfig.mapper.ConnectionConfigDataAccessMapper;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportEntity;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportParameterEntity;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportParameterViewEntity;
import com.bergamota.jasperreports.domain.core.entities.Report;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import com.bergamota.jasperreports.domain.core.entities.ReportParameterView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReportDataAccessMapper {

    private final CategoryDataAccessMapper categoryDataAccessMapper;
    private final ConnectionConfigDataAccessMapper connectionConfigDataAccessMapper;

    public ReportEntity dataAccessEntity(Report report){
        if(report == null)
            return null;

        return ReportEntity.builder()
                .category(categoryDataAccessMapper.dataAccessEntity(report.getCategory()))
                .subReports(report.getSubReports().stream().map(this::dataAccessEntity).collect(Collectors.toSet()))
                .connection(connectionConfigDataAccessMapper.dataAccessEntity(report.getConnectionConfig()))
                .parent(dataAccessEntity(report.getParent()))
                .parameters(report.getParameters().stream().map(this::reportParameterDataAccessEntity).collect(Collectors.toSet()))
                .fileName(report.getFileName())
                .filePath(report.getFilePath())
                .name(report.getName())
                .id(report.getId())
                .build();

    }

    public Report domainEntity(ReportEntity report){
        return domainEntity(report, true,true);
    }
    public Report domainEntity(ReportEntity report, boolean fillParent, boolean fillSubReport){
        if(report == null)
            return null;

        Set<Report> subReports = null;
        Report parent = null;

        if(fillSubReport)
            subReports = report.getSubReports().stream().map(e -> domainEntity(e,false,false)).collect(Collectors.toSet());

        if(fillParent)
            parent = domainEntity(report.getParent(),false,false);

        return Report.builder()
                .category(categoryDataAccessMapper.domainEntity(report.getCategory()))
                .subReports(subReports)
                .connectionConfig(connectionConfigDataAccessMapper.domainEntity(report.getConnection()))
                .parent(parent)
                .parameters(report.getParameters().stream().map(e -> reportParameterDomainEntity(e,false)).collect(Collectors.toSet()))
                .fileName(report.getFileName())
                .filePath(report.getFilePath())
                .id(report.getId())
                .name(report.getName())
                .build();

    }
    private ReportParameter reportParameterDomainEntity(ReportParameterEntity reportParameterEntity){
        return reportParameterDomainEntity(reportParameterEntity, true);
    }
    private ReportParameter reportParameterDomainEntity(ReportParameterEntity reportParameterEntity, boolean fillReport){
        Report report = null;
        if(fillReport)
            report = domainEntity(reportParameterEntity.getReport(), false,false);

        return ReportParameter.builder()
                .id(reportParameterEntity.getId())
                .reportType(reportParameterEntity.getReportType())
                .name(reportParameterEntity.getName())
                .type(reportParameterEntity.getType())
                .defaultValue(reportParameterEntity.getDefaultValue())
                .pattern(reportParameterEntity.getPattern())
                .report(report)
                .build();
    }
    private ReportParameterEntity reportParameterDataAccessEntity(ReportParameter reportParameter){
        return ReportParameterEntity.builder()
                .id(reportParameter.getId())
                .reportType(reportParameter.getReportType())
                .name(reportParameter.getName())
                .type(reportParameter.getType())
                .defaultValue(String.valueOf(reportParameter.getDefaultValue()))
                .pattern(reportParameter.getPattern())
                .report(dataAccessEntity(reportParameter.getReport()))
                .reportParameterView(dataAccessReportParameterViewEntity(reportParameter.getReportParameterView()))
                .build();
    }

    private ReportParameterViewEntity dataAccessReportParameterViewEntity(ReportParameterView reportParameterView){
        return ReportParameterViewEntity.builder()
                .label(reportParameterView.getLabel())
                .visible(reportParameterView.isVisible())
                .required(reportParameterView.isRequired())
                .sortOrder(reportParameterView.getSortOrder())
                .id(reportParameterView.getId())
                .build();
    }
}