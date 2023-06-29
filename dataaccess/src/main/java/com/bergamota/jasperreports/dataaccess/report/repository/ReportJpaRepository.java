package com.bergamota.jasperreports.dataaccess.report.repository;

import com.bergamota.jasperreports.dataaccess.connectionconfig.entities.ConnectionConfigEntity;
import com.bergamota.jasperreports.domain.core.entities.*;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportJpaRepository extends JpaRepository<ReportEntity, Long> {

    List<ReportEntity> findByConnection(ConnectionConfigEntity connectionConfigEntity);
    List<ReportEntity> findByCategory(Category category);
}
