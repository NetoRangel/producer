package com.neto.producer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ProductList {

    private @Id @GeneratedValue Long idProductList;
    private Long idRequest;
    private Long idProduct;
    private Long quantity;

    public ProductList(){}

    public ProductList(Long idRequest, Long idProduct, Long quantity) {
        this.idRequest = idRequest;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public Long getIdProductList() {
        return idProductList;
    }

    public void setIdProductList(Long idProductList) {
        this.idProductList = idProductList;
    }

    public Long getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Long idRequest) {
        this.idRequest = idRequest;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity =  quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductList that = (ProductList) o;
        return idProductList.equals(that.idProductList)
                && idRequest.equals(that.idRequest)
                && idProduct.equals(that.idProduct)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductList, idRequest, idProduct, quantity);
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "idProductList=" + idProductList +
                ", idRequest=" + idRequest +
                ", idProduct=" + idProduct +
                ", quantity=" + quantity +
                '}';
    }
}