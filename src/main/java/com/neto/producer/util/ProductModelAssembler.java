package com.neto.producer.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.neto.producer.model.Product;
import com.neto.producer.rest.ProductController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product product) {

        return EntityModel.of(product, //
                linkTo(methodOn(ProductController.class).one(product.getIdProduct())).withSelfRel(),
                linkTo(methodOn(ProductController.class).all()).withRel("products"));
    }
}