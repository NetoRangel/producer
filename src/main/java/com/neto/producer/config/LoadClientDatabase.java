package com.neto.producer.config;

import com.neto.producer.model.Client;
import com.neto.producer.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadClientDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadClientDatabase.class);

    @Bean
    CommandLineRunner initClientDatabase(ClientRepository clientRepository) {
        return args -> {
            log.info("Preloading " + clientRepository.save(new Client("Amanda")));
            log.info("Preloading " + clientRepository.save(new Client("Beto")));
            log.info("Preloading " + clientRepository.save(new Client("Carla")));
        };
    }
}