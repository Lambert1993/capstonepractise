package com.ecommerce.productcatalogservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class DummyJsonProductDto {

    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;
    private String thumbnail;
    private String category;

}
