package com.bergamota.jasperreports.dataaccess.category.mapper;

import com.bergamota.jasperreports.dataaccess.category.entities.CategoryEntity;
import com.bergamota.jasperreports.domain.core.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDataAccessMapper {

    public Category domainEntity(CategoryEntity categoryEntity){
        if(categoryEntity == null)
            return null;

        return Category.builder()
                .description(categoryEntity.getDescription())
                .id(categoryEntity.getId())
                .parent(domainEntity(categoryEntity.getParent()))
                .path(categoryEntity.getPath())
                .build();
    }
    public CategoryEntity dataAccessEntity(Category category){
        if(category == null)
            return null;

        return CategoryEntity.builder()
                .description(category.getDescription())
                .id(category.getId())
                .parent(dataAccessEntity(category.getParent()))
                .path(category.getPath())
                .build();
    }

}
