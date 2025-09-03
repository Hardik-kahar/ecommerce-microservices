package com.ecommerce.order_service.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://PRODUCT-SERVICE").build();
    }

    public String getProducts() {
        return webClient.get()
                .uri("/product")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
