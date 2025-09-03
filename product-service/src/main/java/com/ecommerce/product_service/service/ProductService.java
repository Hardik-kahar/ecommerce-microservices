package com.ecommerce.product_service.service;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

//    private final ProductClient productClient;
//
//    public ProductService(ProductClient productClient) {
//        this.productClient = productClient;
//    }
//
//    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackProducts")
//    public String fetchProducts() {
//        return productClient.getProducts();
//    }
//
//    public String fallbackProducts(Throwable throwable) {
//        return "Product-Service is down. Please try again later.";
//    }
}