package com.example.storeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stock;
}
