package com.bergamota.jasperreports.domain.application.service.category;

import com.bergamota.jasperreports.domain.application.service.input.services.CategoryApplicationService;
import com.bergamota.jasperreports.domain.application.service.output.repository.CategoryRepository;
import com.bergamota.jasperreports.domain.core.entities.Category;
import com.bergamota.jasperreports.domain.core.entities.CategoryTree;
import com.bergamota.jasperreports.domain.core.exceptions.CategoryDomainException;
import com.bergamota.jasperreports.domain.core.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CategoryApplicationServiceImpl implements CategoryApplicationService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        if(category.getParent() != null && category.getParent().getId() != null) {
            var parent = findById(category.getParent().getId());
            //category = category.withParent(parent);
            category.setPath(parent.getPath() + "/" + category.getDescription());
        }else {
            category.setPath("/" + category.getDescription());
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(Category.class, "id", id));
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll("", null);
    }
    @Override
    public List<Category> findAll(String description, Long parentId) {
        return categoryRepository.findAll(description, parentId);
    }


    @Override
    public List<CategoryTree> getCategoriesAsTree() {
        List<Category> categories = categoryRepository.findAll("", null);

        List<CategoryTree> categoriesDTO = categories.stream().map(CategoryTree::of).toList();

        return categoriesDTO;

    }
    @Override
    public void remove(Long id) {
        var category = findById(id);
        var children = categoryRepository.findByParent(category);
        if(children.size() > 0)
            throw new CategoryDomainException("Category have a children");

        categoryRepository.remove(id);

    }


}
