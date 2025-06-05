package com.example.storeapi.controller;
import com.example.storeapi.config.R;
import com.example.storeapi.dto.OrderRequest;
import com.example.storeapi.pojo.Order;
import com.example.storeapi.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping()
    public R createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @RequestMapping("/{id}")
    public R<Optional<Order>> getOrder(@PathVariable Integer id) {
        return R.success(orderService.getOrderById(id));
    }
}
