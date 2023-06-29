package com.bergamota.jasperreports.domain.application.service.output.repository;

import com.bergamota.jasperreports.domain.application.service.common.CrudRepository;
import com.bergamota.jasperreports.domain.core.entities.ReportConfig;

import java.util.List;

public interface ReportConfigRepository extends CrudRepository<ReportConfig, String> {

    List<ReportConfig> findAll();

}
