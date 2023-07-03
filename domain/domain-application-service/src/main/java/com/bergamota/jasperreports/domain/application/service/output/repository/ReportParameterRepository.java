package com.bergamota.jasperreports.domain.application.service.output.repository;

import com.bergamota.jasperreports.domain.application.service.common.CrudRepository;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;

import java.util.List;

public interface ReportParameterRepository extends CrudRepository<ReportParameter, Long> {

    List<ReportParameter> findByReport(Long reportId);
}
