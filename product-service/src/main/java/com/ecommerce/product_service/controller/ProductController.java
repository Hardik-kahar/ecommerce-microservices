package com.ecommerce.product_service.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final WebClient.Builder webClientBuilder;

    public ProductController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Product service running...............";
    }

    @GetMapping
    @CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
    public String getProducts() {
        // Calling Order-Service
        String orderResponse = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/order")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return "Product Service running... | Order Service says: " + orderResponse;
    }

    // fallback method
    public String orderFallback(Exception e) {
        return "Product Service running... | Order Service is down! Showing fallback response.";
    }

}