package com.ecommerce.productcatalogservice.service;

import com.ecommerce.productcatalogservice.dto.CategoryProductCountDto;

import java.util.List;

public interface ICategoryService {

    List<CategoryProductCountDto> getAllCategoriesWithProductCount();

    Double getAvgPriceForCategory(String category);
}
