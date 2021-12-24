package com.neto.producer.rest;

import java.util.List;
import java.util.stream.Collectors;
import com.neto.producer.model.Product;
import com.neto.producer.repository.ProductRepository;
import com.neto.producer.util.ProductModelAssembler;
import com.neto.producer.util.ProductNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RestController
public class ProductController {

    private final ProductRepository repository;
    private final ProductModelAssembler assembler;

    ProductController(ProductRepository repository, ProductModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all() {

        List<EntityModel<Product>> product = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(product, linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> one(@PathVariable Long id) {

        Product product = repository.findById(id) //
                .orElseThrow(() -> new ProductNotFoundException(id));

        return assembler.toModel(product);
    }

    @PostMapping("/products")
    public ResponseEntity<?> newProduct(@RequestBody Product newProduct) {

        EntityModel<Product> entityModel = assembler.toModel(repository.save(newProduct));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {

        Product updateProduct = repository.findById(id) //
                .map(product -> {
                    product.setIdProduct(id);
                    product.setProductName(newProduct.getProductName());
                    product.setProductValue(newProduct.getProductValue());
                    return repository.save(product);
                }) //
                .orElseGet(() -> {
                    newProduct.setIdProduct(id);
                    return repository.save(newProduct);
                });

        EntityModel<Product> entityModel = assembler.toModel(updateProduct);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.ok().body("Product " + id + " deleted successfully");
        }
        else {
           return ResponseEntity //
                   .status(500).body("Product " + id + " not found");
        }
    }

}