package com.cris.mvc.catalogproducts.repositories;

import com.cris.mvc.catalogproducts.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findByName(String name);
    Optional<List<Product>> findByValueBetween(Double minValue, Double maxValue);
    @Query("SELECT p FROM product p WHERE p.category.name = :name")
    Optional<Set<Product>> findByCategoryName(String name);

    @Modifying
    @Query("UPDATE product p SET p.category.id = :categoryId WHERE p.id = :productId")
    void updateCategory(@Param("productId") Long productId, @Param("categoryId") Long categoryId);

    List<Product> findByNameContaining(String name);

}
