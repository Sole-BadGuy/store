package com.example.storeapi.dto;

import com.example.storeapi.pojo.Order;
import com.example.storeapi.pojo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse extends Order {
    Product product;
}