package com.cris.mvc.catalogproducts.services.impl;

import com.cris.mvc.catalogproducts.dtos.CategoryDTO;
import com.cris.mvc.catalogproducts.dtos.ProductDTO;
import com.cris.mvc.catalogproducts.models.Category;
import com.cris.mvc.catalogproducts.models.Product;
import com.cris.mvc.catalogproducts.repositories.ProductRepository;
import com.cris.mvc.catalogproducts.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findByCategoryName(String name) {
        Optional<Set<Product>> products = productRepository.findByCategoryName(name);
        return products.map(productSet -> productSet.stream().map(this::mapToProductDTO).toList()).orElse(null);
    }

    @Override
    public void createProduct(ProductDTO productDTO) {
        productRepository.save(this.mapToProduct(productDTO));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductDTO).toList();
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setImage(product.getImage());
        productDTO.setDescription(product.getDescription());
        productDTO.setStock(product.getStock());
        productDTO.setValue(product.getValue());
        productDTO.setCategoryDTO(this.mapToCategoryDTO(product.getCategory()));
        return productDTO;
    }

    private CategoryDTO mapToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setCode(category.getCode());
        categoryDTO.setImage(category.getImage());
        return categoryDTO;
    }

    private Product mapToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setImage(productDTO.getImage());
        product.setDescription(productDTO.getDescription());
        product.setStock(productDTO.getStock());
        product.setValue(productDTO.getValue());
        product.setCategory(this.mapToCategory(productDTO.getCategoryDTO()));
        return product;
    }

    private Category mapToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setCode(categoryDTO.getCode());
        category.setImage(categoryDTO.getImage());
        return category;
    }
}
