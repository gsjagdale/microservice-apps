package com.order.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.order.entity.OrderEntity;
import com.order.to.Order;
import com.order.to.Product;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "products", expression = "java(mapProducts(entity.getProductIds(), srcProductMap))")
    Order orderEntityToOrder(OrderEntity entity, @Context Map<Long, Product> srcProductMap);

    //@Mapping(target = "products", expression = "java(mapProducts(entity.getProductIds(), srcProductMap))")
    List<Order> orderEntitiesToOrders(List<OrderEntity> orderEntities, @Context Map<Long, Product> srcProductMap);

    default List<Product> mapProducts(List<Long> productIds, Map<Long, Product> srcProductMap) {
        return productIds.stream().map(id -> srcProductMap.getOrDefault(id, Product.builder().id(id).build())).toList();
    }
}
