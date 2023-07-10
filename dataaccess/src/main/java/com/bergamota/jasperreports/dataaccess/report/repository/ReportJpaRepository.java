package com.bergamota.jasperreports.dataaccess.report.repository;

import com.bergamota.jasperreports.dataaccess.connectionconfig.entities.ConnectionConfigEntity;
import com.bergamota.jasperreports.domain.core.entities.*;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportJpaRepository extends JpaRepository<ReportEntity, Long> {

    List<ReportEntity> findByConnection(ConnectionConfigEntity connectionConfigEntity);
    List<ReportEntity> findByCategory(Category category);

    @Query("SELECT r FROM ReportEntity r WHERE UPPER(r.name) LIKE CONCAT('%',UPPER(:reportName),'%') " +
            " AND r.category.id = COALESCE(:categoryId, r.category.id) " +
            " AND UPPER(r.category.path) LIKE CONCAT('%',UPPER(:categoryPath),'%') " +
            " AND r.parent IS NULL")
    List<ReportEntity> findByNameAndCategoryAndCategoryPath(String reportName, Long categoryId, String categoryPath);
}
