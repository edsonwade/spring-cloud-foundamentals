package com.pluralsight.orderservice.controller;

import com.pluralsight.orderservice.client.CoffeeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@RestController
public class OrderController {

    private final CoffeeClient coffeeClient;

    @Value("${order.default-message}")
    private String defaultOrderMessage;

    public OrderController(CoffeeClient coffeeClient) {
        this.coffeeClient = coffeeClient;
    }

    @GetMapping(value = "/order", produces = MediaType.TEXT_HTML_VALUE)
    public String placeOrder() {
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
              .coffee-name {
                font-weight: 600;
                color: #3E2723;
              }
            </style>
          </head>
          <body>
            <div class="container">
                <div class="order-box">
                  <h1>%s Your Order</h1>
                  <p>%s <span class="coffee-name">%s</span></p>
                </div>
            </div>
          </body>
        </html>
        """, coffeeIconSvg, defaultOrderMessage, coffee);
    }
}
