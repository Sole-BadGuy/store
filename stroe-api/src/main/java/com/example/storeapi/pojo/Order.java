package com.example.storeapi.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;
}