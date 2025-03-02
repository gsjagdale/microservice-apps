package com.order.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.order.entity.OrderEntity;
import com.order.to.Order;
import com.order.to.Product;

@SpringBootTest(classes = OrderMapper.class)
@Disabled
public class OrderMapperTest {

    @Autowired
    private OrderMapper mapper;

    @Test
    public void mapperTest() {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(123L)
                .orderName("test")
                .productIds(List.of(1L, 2L))
                .build();

        List<Product> products = List.of(
                Product.builder()
                        .id(1L)
                        .productName("abc")
                        .build(),
                Product.builder()
                        .id(2L)
                        .productName("pqr")
                        .build());

        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, p -> p));

        Order order = mapper.orderEntityToOrder(orderEntity, productMap);

        Assert.notNull(order, "order should not null");
        Assert.notNull(order.getProducts(), "products should not null");
        Assert.notEmpty(order.getProducts(), "products shouldn't be empty");
        Assert.isTrue(order.getProducts().size() == 2, "products size should be 2");
    }

}
