package com.bergamota.jasperreports.dataaccess.report.repository;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportParameterJpaRepository extends JpaRepository<ReportParameterEntity, Long> {
}
