package com.order.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.order.entity.OrderEntity;
import com.order.repository.OrderRepository;
import com.order.service.OrderService;
import com.order.to.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
@AllArgsConstructor
@Slf4j
public class OrderController {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderService orderService;

    @GetMapping("{orderId}")
    public Order getOrder(@PathVariable("orderId") final Long orderId) {
        return orderService.order(orderId);
    }

    @GetMapping
    public List<Order> allOrders() {
        log.info("request received for list orders");
        log.debug("Input request {}", "NO DATA");
        return orderService.listOrders();
    }

    @GetMapping("minified/{orderId}")
    public OrderEntity getOrderMinified(@PathVariable("orderId") final Long orderId) {
        log.info("request received for order by ID");
        log.debug("Input request {}", orderId.longValue());

        return orderRepository.findById(orderId).orElse(null);
    }

    @GetMapping("minified")
    public List<OrderEntity> allOrdersMinified() {
        log.info("request received for list minified orders");
        return orderRepository.findAll();
    }

    @PostMapping
    public Long saveOrder(@RequestBody final OrderEntity orderEntity) {
        return this.orderRepository.saveAndFlush(orderEntity).getId();
    }

}
