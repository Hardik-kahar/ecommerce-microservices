package com.ecommerce.order_service.service;

import com.ecommerce.order_service.config.ProductClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackProducts")
    public String fetchProducts() {
        return productClient.getProducts();
    }

    public String fallbackProducts(Throwable throwable) {
        return "Product-Service is down. Please try again later.";
    }

    @Retry(name = "productService", fallbackMethod = "fallbackProducts")
    public String fetchProductsRetry() {
        return productClient.getProducts();
    }

    @RateLimiter(name = "productService", fallbackMethod = "fallbackRateLimit")
    public String fetchProductsRateLimiter() {
        return productClient.getProducts();
    }

    public String fallbackRateLimit(Throwable t) {
        return "Too many requests. Please try again later.";
    }

    @Bulkhead(name = "productService", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "fallbackBulkhead")
    public String fetchProductsBulkhead() {
        return productClient.getProducts();
    }

    public String fallbackBulkhead(Throwable t) {
        return "System is busy. Please try later.";
    }

    @TimeLimiter(name = "productService")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackProducts")
    public CompletableFuture<String> fetchProductsAsync() {
        return CompletableFuture.supplyAsync(() -> productClient.getProducts());
    }

}
