package com.bergamota.jasperreports.domain.application.service.output.repository;

import com.bergamota.jasperreports.domain.application.service.common.CrudRepository;
import com.bergamota.jasperreports.domain.core.entities.Report;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Long> {

    List<Report> findByCategory(Long categoryId);
    List<Report> findAll(String reportName, Long categoryId, String categoryPath);
}
