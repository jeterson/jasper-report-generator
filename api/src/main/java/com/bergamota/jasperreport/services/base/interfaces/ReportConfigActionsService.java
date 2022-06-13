package com.bergamota.jasperreport.services.base.interfaces;

import java.util.List;

import com.bergamota.jasperreport.entities.ReportConfig;

public interface ReportConfigActionsService {

    ReportConfig getConfig();
    void save(ReportConfig config);
    void deleteById(String id);
    List<ReportConfig> getAll();

   

}
