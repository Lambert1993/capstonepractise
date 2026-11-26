package com.ecommerce.productcatalogservice.controller;


import com.ecommerce.productcatalogservice.dto.MeaningHitDto;
import com.ecommerce.productcatalogservice.dto.ProductDto;
import com.ecommerce.productcatalogservice.dto.SearchRequestDto;
import com.ecommerce.productcatalogservice.mappers.ProductMapper;
import com.ecommerce.productcatalogservice.service.MeaningSearchService;
import com.ecommerce.productcatalogservice.service.SearchService;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final ProductMapper productMapper;
    private final MeaningSearchService meaningSearchService;

    @PostMapping
    public PagedModel<ProductDto> postSearch(@RequestBody SearchRequestDto searchRequestDto) {
        return new PagedModel<>(searchService.search(searchRequestDto)
                .map(productMapper::toDto));
    }

    @GetMapping
    public PagedModel<ProductDto> getSearch(SearchRequestDto searchRequestDto) {
        return new PagedModel<>(searchService.search(searchRequestDto)
                .map(productMapper::toDto));
    }

    @GetMapping("/meaning")
    public List<MeaningHitDto> meaning(@RequestParam String query) {
        return meaningSearchService.search(query, 5);
    }
}
