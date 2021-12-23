package com.neto.producer.util;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String clientName) {
        super("Could not find Client Name " + clientName);
    }
}