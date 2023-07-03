package com.bergamota.jasperreports.dataaccess.report.repository;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportEntity;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportParameterJpaRepository extends JpaRepository<ReportParameterEntity, Long> {

    List<ReportParameterEntity> findByReport(ReportEntity report);
}
