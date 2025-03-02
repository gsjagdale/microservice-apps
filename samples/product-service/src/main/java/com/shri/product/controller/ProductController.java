package com.shri.product.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shri.product.entity.ProductEntity;
import com.shri.product.repository.ProductRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("products")
@AllArgsConstructor
@Slf4j
public class ProductController {

    @Autowired
    private final ProductRepository productRepository;

    @GetMapping
    public List<ProductEntity> allProducts() {
        log.info("Inside list product service");
        return this.productRepository.findAll();
    }


    @PostMapping
    public Long addProduct(@RequestBody final ProductEntity productEntity) {
        return this.productRepository.saveAndFlush(productEntity).getId();
    }

}
