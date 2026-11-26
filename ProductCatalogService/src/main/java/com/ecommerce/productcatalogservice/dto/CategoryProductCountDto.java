package com.ecommerce.productcatalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductCountDto {

    private UUID id;
    private String name;
    private String description;
    private Long productCount;
}
