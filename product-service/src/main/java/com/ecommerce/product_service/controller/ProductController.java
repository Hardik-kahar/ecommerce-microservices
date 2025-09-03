package com.ecommerce.product_service.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("product")
public class ProductController {

    private final WebClient.Builder webClientBuilder;

    public ProductController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping
    public String getStatus() {
        return "Product service running...............";
    }

    @GetMapping("/order-data")
    @CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
    public String getProducts() {
        // Calling Order-Service via Eureka
        String orderResponse = webClientBuilder.build()
                .get()
                .uri("http://ORDER-SERVICE/order")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return "Product Service running... | Order Service says: " + orderResponse;
    }

    // fallback method
    public String orderFallback(Throwable e) {
        return "Product Service running... | Order Service is down! Fallback activated.";
    }

}