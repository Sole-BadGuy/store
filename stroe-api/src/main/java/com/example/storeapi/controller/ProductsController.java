package com.example.storeapi.controller;

import com.example.storeapi.config.R;
import com.example.storeapi.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping()
    public R getAllProducts() {
        return R.success(productService.getAllProducts());
    }

}
