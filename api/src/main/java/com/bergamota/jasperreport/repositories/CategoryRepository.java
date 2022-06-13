package com.bergamota.jasperreport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bergamota.jasperreport.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByDescription(String description);
	List<Category> findByParent(Category parent);
}
