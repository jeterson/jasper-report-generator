package com.bergamota.jasperreports.dataaccess.category.repository;

import com.bergamota.jasperreports.dataaccess.category.entities.CategoryEntity;
import com.bergamota.jasperreports.dataaccess.report.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByParent(CategoryEntity parent);
    @Query("SELECT c FROM CategoryEntity c WHERE UPPER(c.description) LIKE CONCAT('%', UPPER(:description), '%') " +
            "AND CASE WHEN c.parent IS NULL " +
            "         THEN 0 " +
            "         ELSE c.parent.id " +
            "         END = CASE WHEN :parentId IS NULL THEN COALESCE(c.parent.id, 0) ELSE :parentId END")
    List<CategoryEntity> findByParentAndDescriptionContainingIgnoreCase(@Param("parentId") Long parentId, @Param("description") String description);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CategoryEntity c WHERE EXISTS(SELECT r FROM ReportEntity r WHERE r.category.id = c.id)")
    Boolean hasReportForCategory(Long categoryId);

}
