package com.pluralsight.orderservice.controller;

import com.pluralsight.orderservice.client.CoffeeClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final CoffeeClient coffeeClient;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Value("${server.port}")
    private String port;

    public OrderController(CoffeeClient coffeeClient) {
        this.coffeeClient = coffeeClient;
    }

    @CircuitBreaker(name = "coffeeBreaker", fallbackMethod = "fallbackCoffeeMessage")
    @GetMapping(value = "/order", produces = MediaType.TEXT_HTML_VALUE)
    public String placeOrder() {
        log.info("placeOrder called on port {}", port);
        String coffee = coffeeClient.getCoffeeMessage();
        if (coffee == null) {
            coffee = "Unknown Coffee";
        }

        String coffeeIconSvg = """
            <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="vertical-align: middle; margin-right: 8px;">
                <path d="M18 8h1a4 4 0 0 1 0 8h-1"/>
                <path d="M2 8h16v9a4 4 0 0 1-4 4H6a4 4 0 0 1-4-4V8z"/>
                <line x1="6" y1="1" x2="6" y2="4"/>
                <line x1="10" y1="1" x2="10" y2="4"/>
                <line x1="14" y1="1" x2="14" y2="4"/>
            </svg>""";

        return String.format("""
        <!DOCTYPE html>
        <html lang="en">
          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Order Confirmation</title>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;600&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
            <style>
              body {
                background-color: #EFEBE9; /* Lighter Coffee Cream */
                font-family: 'Poppins', sans-serif; /* Clean sans-serif font */
                font-weight: 300;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                margin: 0;
                color: #3E2723; /* Dark Brown */
              }
              .container {
                 text-align: center;
              }
              .order-box {
                background: #FFFFFF;
                border-radius: 15px; /* Softer corners */
                padding: 50px 60px; /* More padding */
                display: inline-block;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); /* Softer, larger shadow */
                border: 1px solid #D7CCC8; /* Subtle border */
                max-width: 500px; /* Limit width */
              }
              h1 {
                font-family: 'Playfair Display', serif; /* Elegant serif font for heading */
                color: #5D4037; /* Medium Brown */
                font-size: 2.2em; /* Larger heading */
                margin-top: 0; /* Remove default top margin */
                margin-bottom: 25px; /* Space below heading */
                font-weight: 700;
                display: flex; /* Align icon and text */
                align-items: center; /* Vertically center icon and text */
                justify-content: center; /* Center icon and text */
              }
              p {
                 font-size: 1.1em;
                 line-height: 1.6;
                 color: #6D4C41; /* Slightly lighter brown for text */
              }
              .coffee-name {
                font-weight: 600; /* Bolder font for the coffee name itself */
                color: #3E2723; /* Darkest brown for emphasis */
              }
            </style>
          </head>
          <body>
            <div class="container">
                <div class="order-box">
                  <h1>%s Your Order</h1>
                  <p>Thank you! Your delicious <span class="coffee-name">%s</span> is being prepared.</p>
                  </div>
            </div>
          </body>
        </html>
        """, coffeeIconSvg, coffee);
    }

    public String fallbackCoffeeMessage(Throwable t) {
        String coffeeIconSvg = """
        <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="vertical-align: middle; margin-right: 8px;">
            <path d="M18 8h1a4 4 0 0 1 0 8h-1"/>
            <path d="M2 8h16v9a4 4 0 0 1-4 4H6a4 4 0 0 1-4-4V8z"/>
            <line x1="6" y1="1" x2="6" y2="4"/>
            <line x1="10" y1="1" x2="10" y2="4"/>
            <line x1="14" y1="1" x2="14" y2="4"/>
        </svg>""";

        return String.format("""
        <!DOCTYPE html>
        <html lang="en">
          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Service Unavailable</title>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;600&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
            <style>
              body {
                background-color: #EFEBE9;
                font-family: 'Poppins', sans-serif;
                font-weight: 300;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                margin: 0;
                color: #3E2723;
              }
              .container {
                 text-align: center;
              }
              .order-box {
                background: #FFFFFF;
                border-radius: 15px;
                padding: 50px 60px;
                display: inline-block;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                border: 1px solid #D7CCC8;
                max-width: 500px;
              }
              h1 {
                font-family: 'Playfair Display', serif;
                color: #5D4037;
                font-size: 2.2em;
                margin-top: 0;
                margin-bottom: 25px;
                font-weight: 700;
                display: flex;
                align-items: center;
                justify-content: center;
              }
              p {
                 font-size: 1.1em;
                 line-height: 1.6;
                 color: #6D4C41;
              }
            </style>
          </head>
          <body>
            <div class="container">
                <div class="order-box">
                  <h1>%s Service Unavailable</h1>
                  <p>Oops! Our coffee machine is having a break. In the meantime, why not enjoy a glass of refreshing water?</p>
                </div>
            </div>
          </body>
        </html>
        """, coffeeIconSvg);
    }
}
