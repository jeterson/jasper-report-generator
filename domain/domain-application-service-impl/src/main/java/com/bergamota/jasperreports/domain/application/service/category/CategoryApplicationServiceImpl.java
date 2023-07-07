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

    private List<CategoryTree> buildTree(List<CategoryTree> categories, List<CategoryTree> tree) {

        if(tree == null) {
            tree = categories.stream().filter(c -> c.getParent() == null).toList();
        }

        tree = tree.stream().peek(parentNode -> {
            Predicate<CategoryTree> isChild = node -> node.getParent() != null && node.getParent().getId().equals(parentNode.getId());

            var onlyChildren = new ArrayList<>(categories.stream().filter(isChild).toList());

            List<CategoryTree> children = buildTree(categories, onlyChildren);
            var mutableListChildren = new ArrayList<>(children);

            if(parentNode.getReports() != null) {
                parentNode.getReports().forEach(e -> {
                    if(!e.isSubReport()) {
                        mutableListChildren.add(CategoryTree.builder()
                                .label(e.getName())
                                .id(e.getId())
                                .isReport(true)
                                .build());
                    }
                });
            }
            parentNode.setSubItems(mutableListChildren);
        }).toList();

        return tree;
    }

    @Override
    public List<CategoryTree> getCategoriesAsTree() {
        List<Category> categories = categoryRepository.findAll("", null);

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
