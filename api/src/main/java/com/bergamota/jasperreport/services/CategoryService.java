package com.bergamota.jasperreport.services;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bergamota.jasperreport.entities.Category;
import com.bergamota.jasperreport.exceptions.EntityNotFoundException;
import com.bergamota.jasperreport.exceptions.http.BadRequestException;
import com.bergamota.jasperreport.repositories.CategoryRepository;
import com.bergamota.jasperreport.resources.dtos.CategoryTreeDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository repository;

	public Category save(Category c) {
		if(c.getParent() != null && c.getParent().getId() != null) {
			var parent = repository.findById(c.getParent().getId()).get();
			c.setPath(parent.getPath() + "/" + c.getDescription());
		}else {
			c.setPath("/" + c.getDescription());
		}
		return repository.save(c);
	}
	public Category save(Long id, Category c) {
		var aux = repository.findById(id);
		if(!aux.isPresent())
			throw new EntityNotFoundException(Category.class, "id", id);
		
		return save(c);
	}
	public void delete(long id) {
		var children = repository.findByParent(Category.builder().id(id).build());
		if(children.size() == 0)
			repository.deleteById(id);
		else
			throw new BadRequestException("Categoria possui outras categorias filhas");
	}
	public Category findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<Category> findAll(String filter) {
		return repository.findAll().stream().filter(c -> c.getDescription().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
	}
	
	private List<CategoryTreeDTO> buildTree(List<CategoryTreeDTO> categories, List<CategoryTreeDTO> tree) {

		if(tree == null) {
			tree = categories.stream().filter(c -> c.getParent() == null).collect(Collectors.toList());
		}

		tree = tree.stream().map(parentNode -> {
			Predicate<CategoryTreeDTO> isChild = node -> node.getParent() == null ? false : node.getParent().getId().equals(parentNode.getId());

			var onlyChildren = categories.stream().filter(isChild).collect(Collectors.toList());

			List<CategoryTreeDTO> children = buildTree(categories, onlyChildren);
			parentNode.setSubItems(children);
			return parentNode;
		}).collect(Collectors.toList());

		return tree;
	}

	public List<CategoryTreeDTO> getCategoriesAsTree() {
		List<Category> categories = repository.findAll();

		List<CategoryTreeDTO> categoriesDTO = categories.stream().map(c -> {
			var dto = CategoryTreeDTO.of(c);
			return dto;
		}).collect(Collectors.toList());

		var tree =  buildTree(categoriesDTO, null);
		return tree;

	}



}
