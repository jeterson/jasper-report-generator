package com.bergamota.jasperreports.dataaccess.report.mapper;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportConfigEntity;
import com.bergamota.jasperreports.domain.core.entities.ReportConfig;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportConfigDataAccessMapper {

    public ReportConfig domainEntity(ReportConfigEntity reportConfigEntity){
        return ReportConfig.builder()
                .key(reportConfigEntity.getKey())
                .generatedReportBasePath(reportConfigEntity.getGeneratedReportBasePath())
                .active(reportConfigEntity.isActive())
                .basePath(reportConfigEntity.getBasePath())
                .imagePath(reportConfigEntity.getImagePath())
                .build();
    }
    public ReportConfigEntity dataAccessEntity(ReportConfig reportConfig){
        return ReportConfigEntity.builder()
                .active(reportConfig.isActive())
                .basePath(reportConfig.getBasePath())
                .generatedReportBasePath(reportConfig.getGeneratedReportBasePath())
                .imagePath(reportConfig.getImagePath())
                .key(reportConfig.getKey())
                .build();
    }
}
