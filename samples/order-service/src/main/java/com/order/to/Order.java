package com.order.to;

import java.util.List;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private String orderName;
    private List<Product> products;
}
