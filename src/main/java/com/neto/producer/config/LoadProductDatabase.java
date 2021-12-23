package com.neto.producer.config;

import com.neto.producer.model.Product;
import com.neto.producer.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadProductDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadProductDatabase.class);

    @Bean
    CommandLineRunner initProductDatabase(ProductRepository productRepository) {
        return args -> {
            log.info("Preloading " + productRepository.save(new Product("Arroz", 15.00F)));
            log.info("Preloading " + productRepository.save(new Product("Feijao", 5.50F)));
            log.info("Preloading " + productRepository.save(new Product("Macarrao", 2.99F)));
        };
    }
}