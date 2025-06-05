package com.example.storeapi.service;

import com.example.storeapi.config.R;
import com.example.storeapi.dto.OrderRequest;
import com.example.storeapi.dto.OrderResponse;
import com.example.storeapi.pojo.Order;
import com.example.storeapi.pojo.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {
    private final ProductService productService;
    private final List<Order> orders = new ArrayList<>();
    private final AtomicInteger orderIdCounter = new AtomicInteger(1);

    public OrderService(ProductService productService) {
        this.productService = productService;
    }

    public R createOrder(OrderRequest request) {
        ArrayList<OrderResponse> responses = new ArrayList<>();
        for (int i = 0; i < request.getOrders().size(); i++) {
            Order curOrder = request.getOrders().get(i);
            Product product = productService.getProductById(curOrder.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("未找到商品"));

            if (product.getStock() < curOrder.getQuantity()) {
                return R.failed("超出库存数量");
            }
            BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(curOrder.getQuantity()));
            Order order = new Order(
                    orderIdCounter.getAndIncrement(),
                    product.getId(),
                    curOrder.getQuantity(),
                    totalPrice,
                    LocalDateTime.now()
            );
            OrderResponse orderResponse = new OrderResponse();
            BeanUtils.copyProperties(order, orderResponse);
            orderResponse.setProduct(product);
            orderResponse.setProduct(product);
            this.orders.add(order);
            responses.add(orderResponse);
            productService.updateProductStock(product.getId(), curOrder.getQuantity());
        }
        return R.success(responses);
    }

    public Optional<Order> getOrderById(Integer id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }
}
