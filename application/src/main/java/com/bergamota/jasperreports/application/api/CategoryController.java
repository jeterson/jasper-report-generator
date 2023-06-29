package com.bergamota.jasperreports.application.api;

import com.bergamota.jasperreports.domain.application.service.dto.category.CategoryRequestFilter;
import com.bergamota.jasperreports.domain.application.service.dto.category.CreateCategoryCommand;
import com.bergamota.jasperreports.domain.application.service.input.services.CategoryApplicationService;
import com.bergamota.jasperreports.domain.core.entities.CategoryTree;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bergamota.jasperreports.domain.core.entities.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryApplicationService categoryApplicationService;

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        var category = categoryApplicationService.findById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll(CategoryRequestFilter categoryRequestFilter){
        var categories = categoryApplicationService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTree>> findCategoriesAsTree(){
        var categoriesTree = categoryApplicationService.getCategoriesAsTree();
        return ResponseEntity.ok(categoriesTree);
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CreateCategoryCommand categoryCommand){
        var categoryParent = categoryCommand.parentId() == null ? null : Category.builder().id(categoryCommand.parentId()).build();
        var categoryCreated = categoryApplicationService.create(Category.builder()
                        .description(categoryCommand.description())
                        .parent(categoryParent)
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody CreateCategoryCommand updateCategoryCommand){
        var categoryParent = updateCategoryCommand.parentId() == null ? null : Category.builder().id(updateCategoryCommand.parentId()).build();
        var categoryUpdated = categoryApplicationService.create(Category.builder()
                        .id(id)
                        .parent(categoryParent)
                        .description(updateCategoryCommand.description())
                .build());
        return ResponseEntity.ok(categoryUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryApplicationService.remove(id);
        return ResponseEntity.noContent().build();
    }
}