package com.neto.producer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {

    private @Id @GeneratedValue Long idProduct;
    private String productName;
    private Float productValue;

    public Product(){}

    public Product(String productName, Float productValue) {
        this.productName = productName;
        this.productValue = productValue;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductValue() {
        return productValue;
    }

    public void setProductValue(Float productValue) {
        this.productValue = productValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return idProduct.equals(product.idProduct)
                && productName.equals(product.productName)
                && productValue.equals(product.productValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, productName, productValue);
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", productName='" + productName + '\'' +
                ", productValue=" + productValue +
                '}';
    }
}