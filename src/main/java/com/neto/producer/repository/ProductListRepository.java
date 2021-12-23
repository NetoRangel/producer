package com.neto.producer.repository;

import com.neto.producer.model.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductListRepository extends  JpaRepository<ProductList, Long> {

}
