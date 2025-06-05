package com.example.storeapi.service;
import com.example.storeapi.dto.ProductDto;
import com.example.storeapi.pojo.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "苹果", new BigDecimal("15"), 10));
        products.add(new Product(2, "联想电脑", new BigDecimal("3999.99"), 15));
        products.add(new Product(3, "华为手机", new BigDecimal("8949.99"), 20));
    }

    public List<ProductDto> getAllProducts() {
        return products.stream()
                .map(p -> new ProductDto(p.getId(), p.getName(), p.getPrice(), p.getStock()))
                .toList();
    }

    public Optional<Product> getProductById(Integer id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void updateProductStock(Integer productId, Integer quantity) {
        products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .ifPresent(p -> p.setStock(p.getStock() - quantity));
    }
}