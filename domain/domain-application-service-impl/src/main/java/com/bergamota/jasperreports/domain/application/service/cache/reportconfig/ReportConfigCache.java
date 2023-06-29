package com.bergamota.jasperreports.domain.application.service.cache.reportconfig;

import com.bergamota.jasperreports.domain.core.entities.ReportConfig;

import java.util.Optional;

public class ReportConfigCache {

    private static ReportConfigCache instance;
    private ReportConfig reportConfig;
    private ReportConfigCache() {}

    public static ReportConfigCache getInstance(){
        if(instance == null)
            instance = new ReportConfigCache();

        return instance;
    }

    public void invalidate(){
        reportConfig = null;
    }

    public Optional<ReportConfig> getReportConfig(){
        return Optional.ofNullable(reportConfig);
    }
    public void setReportConfig(ReportConfig reportConfig){
        this.reportConfig = reportConfig;
    }

}
