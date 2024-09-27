package com.cris.mvc.catalogproducts.repositories;

import com.cris.mvc.catalogproducts.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    Optional<Category> findByCode(String code);
    Optional<List<Category>> findByNameContaining(String name);
    Optional<List<Category>> findByCodeContaining(String code);
    @Query("SELECT COUNT(p) FROM product p WHERE p.category.id = :id")
    Integer countProductsByCategory(Long id);

}
