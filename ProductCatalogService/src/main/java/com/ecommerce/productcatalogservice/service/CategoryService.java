package com.ecommerce.productcatalogservice.service;

import com.ecommerce.productcatalogservice.dto.CategoryProductCountDto;
import com.ecommerce.productcatalogservice.model.enums.State;
import com.ecommerce.productcatalogservice.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryProductCountDto> getAllCategoriesWithProductCount() {
        log.info("Start to read all categories along with their active product count");
        return categoryRepository.findCategoriesWithProductCount(State.ACTIVE);
    }
}
