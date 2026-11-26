package com.ecommerce.productcatalogservice.service;

import com.ecommerce.productcatalogservice.dto.ProductDto;
import com.ecommerce.productcatalogservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public interface IProductService {


    Product getProductById(UUID id);

    Product createProduct(ProductDto productDto);

    Product updateProduct(UUID id, ProductDto productDto);

    boolean deleteProductById(UUID id);

    List<Product> getAllProducts();

    Page<Product> getProductsInPriceRange(BigDecimal minPrice,
                                          BigDecimal maxPrice,
                                          Pageable pageable);
}
