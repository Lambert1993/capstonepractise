package com.ecommerce.productcatalogservice.repository;

import com.ecommerce.productcatalogservice.dto.CategoryProductCountDto;
import com.ecommerce.productcatalogservice.model.Category;
import com.ecommerce.productcatalogservice.model.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String name);

    @Query("""
            SELECT new com.ecommerce.productcatalogservice.dto.CategoryProductCountDto(
                c.id, c.name, c.description, COUNT(p.id))
            FROM Category c
            LEFT JOIN c.products p WITH p.state = :state
            GROUP BY c.id, c.name, c.description
            ORDER BY c.name
            """)
    List<CategoryProductCountDto> findCategoriesWithProductCount(@Param("state") State state);
}
