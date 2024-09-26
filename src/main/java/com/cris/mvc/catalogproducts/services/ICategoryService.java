package com.cris.mvc.catalogproducts.services;

import com.cris.mvc.catalogproducts.dtos.CategoryDTO;
import com.cris.mvc.catalogproducts.models.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    boolean createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> findAll();
    void updateCategory(CategoryDTO categoryDTO);
    CategoryDTO findById(Long id);
    CategoryDTO findByName(String name);
    CategoryDTO findByCode(String code);
    List<CategoryDTO> findByNameContaining(String name);
    List<CategoryDTO> findByCodeContaining(String code);
    Optional<Category> findCategoryByName(String name);
}
