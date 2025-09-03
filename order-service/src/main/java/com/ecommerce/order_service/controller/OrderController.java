package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("order")
public class OrderController {

    private final ProductService productService;

    public OrderController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getOrders() {
        return "order service running.........;";
    }

    @GetMapping("/product/cb")
    public String productsCircuitBreaker() {
        return productService.fetchProducts();
    }

    @GetMapping("/product/retry")
    public String productsRetry() {
        return productService.fetchProductsRetry();
    }

    @GetMapping("/product/ratelimiter")
    public String productsRateLimiter() {
        return productService.fetchProductsRateLimiter();
    }

    @GetMapping("/product/bulkhead")
    public String productsBulkhead() {
        return productService.fetchProductsBulkhead();
    }

    @GetMapping("/product/timelimiter")
    public CompletableFuture<String> productsTimeLimiter() {
        return productService.fetchProductsAsync();
    }
}
