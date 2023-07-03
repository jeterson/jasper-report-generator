package com.bergamota.jasperreports.dataaccess.category.adapter;

import com.bergamota.jasperreports.common.domain.exception.ConstraintViolationException;
import com.bergamota.jasperreports.dataaccess.category.mapper.CategoryDataAccessMapper;
import com.bergamota.jasperreports.dataaccess.category.repository.CategoryJpaRepository;
import com.bergamota.jasperreports.domain.application.service.output.repository.CategoryRepository;
import com.bergamota.jasperreports.domain.core.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryDataAccessMapper categoryDataAccessMapper;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(Category obj) {
        var categoryEntity = categoryDataAccessMapper.dataAccessEntity(obj);
        return categoryDataAccessMapper.domainEntity(categoryJpaRepository.save(categoryEntity));
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id).map(categoryDataAccessMapper::domainEntity);
    }

    @Override
    public void remove(Long id) {
        if(categoryJpaRepository.hasReportForCategory(id))
            throw new ConstraintViolationException("Category_Report", "category.id join report.categoryId");
        categoryJpaRepository.deleteById(id);
    }

    @Override
    public List<Category> findAll(String description, Long parentId) {
        return categoryJpaRepository.findByParentAndDescriptionContainingIgnoreCase(parentId, description)
                .stream().map(categoryDataAccessMapper::domainEntity).toList();
    }

    @Override
    public List<Category> findByParent(Category parent) {
        return categoryJpaRepository.findByParent(categoryDataAccessMapper.dataAccessEntity(parent)).stream().map(categoryDataAccessMapper::domainEntity).toList();
    }
}
