package com.neto.producer.util;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productName) {
        super("Could not find Client " + productName);
    }
}
