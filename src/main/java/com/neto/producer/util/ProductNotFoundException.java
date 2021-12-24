package com.neto.producer.util;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Could not find Product ID " + id);
    }

    public ProductNotFoundException(String productName) {
        super("Could not find Product Name " + productName);
    }

}
