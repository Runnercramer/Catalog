package com.cris.mvc.catalogproducts.services.impl;

import com.cris.mvc.catalogproducts.dtos.CategoryDTO;
import com.cris.mvc.catalogproducts.dtos.ProductDTO;
import com.cris.mvc.catalogproducts.models.Category;
import com.cris.mvc.catalogproducts.models.Product;
import com.cris.mvc.catalogproducts.repositories.CategoryRepository;
import com.cris.mvc.catalogproducts.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){ this.categoryRepository = categoryRepository; }

    @Override
    public boolean createCategory(CategoryDTO categoryDTO) {
        categoryRepository.save(mapToCategory(categoryDTO));
        return true;
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::mapToCategoryDTO).toList();
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryDTO.getId());
        if(existingCategory.isPresent()){
            existingCategory.get().setName(categoryDTO.getName());
            existingCategory.get().setCode(categoryDTO.getCode());
            if (categoryDTO.getImage() != null) existingCategory.get().setImage(categoryDTO.getImage());
            categoryRepository.save(existingCategory.get());
        }
    }

    @Override
    public CategoryDTO findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::mapToCategoryDTO).orElse(null);
    }

    @Override
    public CategoryDTO findByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.map(this::mapToCategoryDTO).orElse(null);
    }

    @Override
    public CategoryDTO findByCode(String code) {
        Optional<Category> category = categoryRepository.findByCode(code);
        return category.map(this::mapToCategoryDTO).orElse(null);
    }

    @Override
    public List<CategoryDTO> findByNameContaining(String name) {
        Optional<List<Category>> categories = categoryRepository.findByNameContaining(name);
        return categories.map(categoryList -> categoryList.stream().map(this::mapToCategoryDTO).toList()).orElse(null);
    }

    @Override
    public List<CategoryDTO> findByCodeContaining(String code) {
        Optional<List<Category>> categories = categoryRepository.findByCodeContaining(code);
        return categories.map(categoryList -> categoryList.stream().map(this::mapToCategoryDTO).toList()).orElse(null);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    private Category mapToCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setCode(categoryDTO.getCode());
        category.setImage(categoryDTO.getImage());
        return category;
    }

    private CategoryDTO mapToCategoryDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setCode(category.getCode());
        categoryDTO.setImage(category.getImage());

        Set<ProductDTO> productDTOs = category.getProducts().stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toSet());

        categoryDTO.setProducts(productDTOs);

        return categoryDTO;
    }
    private ProductDTO mapToProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setImage(product.getImage());
        productDTO.setDescription(product.getDescription());
        productDTO.setStock(product.getStock());
        productDTO.setValue(product.getValue());
        return productDTO;
    }
}
