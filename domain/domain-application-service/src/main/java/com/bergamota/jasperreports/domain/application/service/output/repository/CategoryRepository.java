package com.bergamota.jasperreports.domain.application.service.output.repository;

import com.bergamota.jasperreports.domain.application.service.common.CrudRepository;
import com.bergamota.jasperreports.domain.core.entities.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findAll(String description, Long parentId);
    List<Category> findByParent(Category parent);
}
