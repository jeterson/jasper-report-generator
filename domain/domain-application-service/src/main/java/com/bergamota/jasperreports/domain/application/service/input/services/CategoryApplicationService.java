package com.bergamota.jasperreports.domain.application.service.input.services;
import com.bergamota.jasperreports.domain.core.entities.*;

import java.util.List;
import java.util.Optional;

public interface CategoryApplicationService {

    Category create(Category category);
    Category findById(Long id);

    Category update(Category category);
    List<Category> findAll();
    List<CategoryTree> getCategoriesAsTree();
    void remove(Long id);

}
