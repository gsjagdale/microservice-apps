package com.order.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.order.to.Product;

@FeignClient("product-service")
public interface ProductClient {

    @GetMapping("products")
    public List<Product> allProducts();

}
