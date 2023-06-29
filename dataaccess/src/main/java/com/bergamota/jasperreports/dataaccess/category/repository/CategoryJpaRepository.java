package com.bergamota.jasperreports.dataaccess.category.repository;

import com.bergamota.jasperreports.dataaccess.category.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByParent(CategoryEntity parent);
}
