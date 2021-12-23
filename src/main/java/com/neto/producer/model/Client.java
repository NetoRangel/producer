package com.neto.producer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Client {

    private @Id @GeneratedValue Long idClient;
    private String clientName;

    public Client(){}

    public Client(String clientName) {
        this.clientName = clientName;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idClient.equals(client.idClient)
                && clientName.equals(client.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, clientName);
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}