package com.ecommerce.productcatalogservice.service;

import com.ecommerce.productcatalogservice.dto.CategoryDto;
import com.ecommerce.productcatalogservice.dto.DummyJsonProductDto;
import com.ecommerce.productcatalogservice.dto.ImportDummyJsonProductDto;
import com.ecommerce.productcatalogservice.dto.ProductDto;
import com.ecommerce.productcatalogservice.model.Category;
import com.ecommerce.productcatalogservice.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service("importService")
@Slf4j
public class ImportProductService {

    private final RestTemplate restTemplate;
    private final StorageProductService storageProductService;

    public ImportProductService(RestTemplateBuilder restTemplateBuilder, StorageProductService storageProductService) {
        this.restTemplate = restTemplateBuilder.build();
        this.storageProductService = storageProductService;
    }


    public String importAllDummyProducts() {
        int count =0 ;
        try {
            log.info("Start to read data for from the supplier dummyjson");
            ResponseEntity<ImportDummyJsonProductDto> jsonDto = restTemplate
                    .getForEntity("https://dummyjson.com/products?limit=0",
                            ImportDummyJsonProductDto.class);
            if(jsonDto.getStatusCode().is2xxSuccessful()){
                count=jsonDto.getBody().getProducts().size();
                jsonDto.getBody().getProducts().forEach(x->
                        storageProductService.createProduct(from(x)));
            }
            else
                return null;
        }catch (HttpClientErrorException.NotFound errorException){
            return null;
        }
        return "Successfully imported:::"+count+" records";
    }

    private ProductDto from(DummyJsonProductDto product) {
        ProductDto dto = new ProductDto();
        //   dto.setId(product.getId());
        dto.setName(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getThumbnail());

        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            // categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory());
            dto.setCategory(categoryDto);
        }
        return dto;
    }


}
