package com.cris.mvc.catalogproducts.services;

import com.cris.mvc.catalogproducts.dtos.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> findByCategoryName(String name);
    void createProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO findById(Long id);
    void updateProduct(ProductDTO productDTO, Long categoryId);
    void deleteProduct(Long id);
}
