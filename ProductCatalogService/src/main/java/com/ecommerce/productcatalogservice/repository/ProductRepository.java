package com.ecommerce.productcatalogservice.repository;

import com.ecommerce.productcatalogservice.model.Product;
import com.ecommerce.productcatalogservice.model.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByIdAndState(UUID id, State state);

    //@Query("SELECT p FROM Product p JOIN FETCH p.category c")
    @EntityGraph(attributePaths = {"category"})
    List<Product> findAllProducts();

    @EntityGraph(attributePaths = {"category"})
    Page<Product> findByPriceBetweenAndState(BigDecimal minPrice, BigDecimal maxPrice, State state, Pageable pageable);

    List<Product> findAllByState(State state);

    Page<Product> findAllByState(State state, Pageable pageable);

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p where p.state = :state "
            + "and (:word is null or p.name like :word or p.description like :word) "
            + "and (:maxPrice is null or p.price <= :maxPrice)")
    Page<Product> search(State state, String word, BigDecimal maxPrice, Pageable pageable);

}
