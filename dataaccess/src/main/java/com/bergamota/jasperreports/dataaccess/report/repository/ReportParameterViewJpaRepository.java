package com.bergamota.jasperreports.dataaccess.report.repository;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportParameterViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportParameterViewJpaRepository extends JpaRepository<ReportParameterViewEntity, Long> {
}
