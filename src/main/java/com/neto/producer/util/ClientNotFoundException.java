package com.neto.producer.util;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id) {
        super("Could not find Client ID " + id);
    }

    public ClientNotFoundException(String clientName) {
        super("Could not find Client Name " + clientName);
    }
}