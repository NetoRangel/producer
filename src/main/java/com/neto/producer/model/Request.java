package com.neto.producer.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRequest;
    private Long idClient;
    private Float requestValue;
    private String deliveryAddress;
    private Date dataPedido;

    public Request(){}

    public Request(Long idClient, Float requestValue, String deliveryAddress, Date dataPedido) {
        this.idClient = idClient;
        this.requestValue = requestValue;
        this.deliveryAddress = deliveryAddress;
        this.dataPedido = dataPedido;
    }

    public Request(Long idClient, String deliveryAdress) {
        this.idClient = idClient;
        this.deliveryAddress = deliveryAdress;
    }

    public Long getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Long idRequest) {
        this.idRequest = idRequest;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Float getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(Float requestValue) {
        this.requestValue = requestValue;
    }

    public String getDeliveryAdress() {
        return deliveryAddress;
    }

    public void setDeliveryAdress(String deliveryAdress) {
        this.deliveryAddress = deliveryAdress;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return idRequest.equals(request.idRequest)
                && idClient.equals(request.idClient)
                && Objects.equals(requestValue, request.requestValue)
                && deliveryAddress.equals(request.deliveryAddress)
                && dataPedido.equals(request.dataPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRequest, idClient, requestValue, deliveryAddress, dataPedido);
    }

    @Override
    public String toString() {
        return "Request{" +
                "idRequest=" + idRequest +
                ", idClient=" + idClient +
                ", requestValue=" + requestValue +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", dataPedido=" + dataPedido +
                '}';
    }
}
