package com.example.storeapi.dto;

import com.example.storeapi.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    ArrayList<Order> orders;
}
