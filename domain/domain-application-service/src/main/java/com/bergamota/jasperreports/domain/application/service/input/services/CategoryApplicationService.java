package com.bergamota.jasperreports.domain.application.service.input.services;
import com.bergamota.jasperreports.domain.core.entities.*;

import java.util.List;

public interface CategoryApplicationService {

    Category create(Category category);
    Category findById(Long id);

    Category update(Category category);
    List<Category> findAll();
    List<Category> findAll(String description, Long parentId);
    List<CategoryTree> getCategoriesAsTree();
    void remove(Long id);

}
