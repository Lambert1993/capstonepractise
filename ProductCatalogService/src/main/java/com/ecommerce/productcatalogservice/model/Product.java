package com.ecommerce.productcatalogservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(indexes = @Index(name = "idx_product_name",columnList = "name"))
public class Product extends BaseEntity{

    private String name;

    @Column(precision = 19,scale = 3)
    private BigDecimal price;

    private String description;

    private String imageUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String embedding;
}
