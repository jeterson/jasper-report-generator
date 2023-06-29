package com.bergamota.jasperreports.dataaccess.report.repository;

import com.bergamota.jasperreports.dataaccess.report.entities.ReportConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportConfigJpaRepository extends JpaRepository<ReportConfigEntity, String> {
}
