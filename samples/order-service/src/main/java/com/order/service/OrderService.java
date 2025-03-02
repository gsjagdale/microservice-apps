package com.order.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.feign.ProductClient;
import com.order.mapper.OrderMapper;
import com.order.repository.OrderRepository;
import com.order.to.Order;
import com.order.to.Product;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderMapper orderMapper;

    @Autowired
    private final ProductClient productClient;

    public List<Order> listOrders() {
        return orderMapper.orderEntitiesToOrders(orderRepository.findAll(), fetchProducts());
    }

    public Order order(Long id) {
        return orderMapper.orderEntityToOrder(orderRepository.findById(id).orElse(null), fetchProducts());
    }

    private Map<Long, Product> fetchProducts() {
        return productClient.allProducts().stream().collect(Collectors.toMap(Product::getId, p -> p));
    }

}
