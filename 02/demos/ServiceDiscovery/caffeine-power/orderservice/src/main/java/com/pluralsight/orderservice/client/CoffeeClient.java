package com.pluralsight.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "coffee-service") // serviceId from Eureka
public interface CoffeeClient {
    @GetMapping("/coffee")
    String getCoffeeMessage();
}
