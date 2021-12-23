package com.neto.producer.repository;

import com.neto.producer.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends  JpaRepository<Client, Long>{

    List<Client> findByClientName(String clientName);

}
