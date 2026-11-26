package com.ecommerce.productcatalogservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductPitchDto {
    UUID id;
    String pitch;
}
