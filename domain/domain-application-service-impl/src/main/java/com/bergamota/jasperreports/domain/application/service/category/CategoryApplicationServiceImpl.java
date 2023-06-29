package com.bergamota.jasperreports.domain.application.service.category;

import com.bergamota.jasperreports.domain.application.service.input.services.CategoryApplicationService;
import com.bergamota.jasperreports.domain.application.service.output.repository.CategoryRepository;
import com.bergamota.jasperreports.domain.core.entities.Category;
import com.bergamota.jasperreports.domain.core.entities.CategoryTree;
import com.bergamota.jasperreports.domain.core.exceptions.CategoryDomainException;
import com.bergamota.jasperreports.domain.core.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryApplicationServiceImpl implements CategoryApplicationService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        if(category.getParent() != null && category.getParent().getId() != null) {
            var parent = findById(category.getParent().getId());
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
        return categoryRepository.findAll();
    }

    private List<CategoryTree> buildTree(List<CategoryTree> categories, List<CategoryTree> tree) {

        if(tree == null) {
            tree = categories.stream().filter(c -> c.getParent() == null).toList();
        }

        tree = tree.stream().peek(parentNode -> {
            Predicate<CategoryTree> isChild = node -> node.getParent() != null && node.getParent().getId().equals(parentNode.getId());

            var onlyChildren = categories.stream().filter(isChild).toList();

            List<CategoryTree> children = buildTree(categories, onlyChildren);
            parentNode.setSubItems(children);
        }).toList();

        return tree;
    }

    @Override
    public List<CategoryTree> getCategoriesAsTree() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryTree> categoriesDTO = categories.stream().map(CategoryTree::of).toList();

        return buildTree(categoriesDTO, null);

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
