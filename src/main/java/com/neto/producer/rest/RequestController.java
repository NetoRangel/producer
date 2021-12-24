package com.neto.producer.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.neto.producer.config.RequestQueueConfig;
import com.neto.producer.model.Client;
import com.neto.producer.model.Product;
import com.neto.producer.model.ProductList;
import com.neto.producer.model.Request;
import com.neto.producer.repository.ClientRepository;
import com.neto.producer.repository.ProductListRepository;
import com.neto.producer.repository.RequestRepository;
import com.neto.producer.repository.ProductRepository;
import com.neto.producer.util.ClientNotFoundException;
import com.neto.producer.util.ProductNotFoundException;
import com.neto.producer.util.RequestModelAssembler;
import com.neto.producer.util.RequestNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RestController
public class RequestController {

    @Autowired
    private RabbitTemplate template;

    private final RequestRepository repository;
    private final RequestModelAssembler assembler;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ProductListRepository productListRepository;

    RequestController(RequestRepository repository, RequestModelAssembler assembler, ProductRepository productRepository, ClientRepository clientRepository, ProductListRepository productListRepository) {
        this.repository = repository;
        this.assembler = assembler;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.productListRepository = productListRepository;
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<Request>> all() {

        List<EntityModel<Request>> orders = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(orders, //
                linkTo(methodOn(RequestController.class).all()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    public EntityModel<Request> one(@PathVariable Long id) {

        Request request = repository.findById(id) //
                .orElseThrow(() -> new RequestNotFoundException(id));

        return assembler.toModel(request);
    }

    @PostMapping("/orders/new")
    public ResponseEntity<EntityModel<Request>> newRequest(@RequestBody JsonNode payload) {

        Float mathVariable = 0F;
        Request newRequest = new Request();
        List<ProductList> productList = new ArrayList<>();

        //Save new ProductList
        Iterator<JsonNode> it = payload.get("productList").iterator();
        while (it.hasNext()) {
            JsonNode payloadObject = it.next();

            List<Product> searchProduct = productRepository.findByProductName(payloadObject.get("productName").textValue());
            if (searchProduct.size() > 0){
                //Convert Product to ProductList
                ProductList productListConverter = new ProductList();
                productListConverter.setIdProduct(searchProduct.get(0).getIdProduct());
                productListConverter.setQuantity(payloadObject.get("quantity").longValue());

                //Adding to the List
                productList.add(productListConverter);

                //Sum of total request value
                mathVariable = mathVariable + payloadObject.get("quantity").longValue() * searchProduct.get(0).getProductValue();

            }  else {
                throw new ProductNotFoundException(payloadObject.get("productName").textValue());
            }
        }

        //Save new Request
        List<Client> searchClient = (clientRepository.findByClientName(payload.get("clientName").textValue()));
        if(searchClient.size() > 0){
            newRequest.setIdClient(searchClient.get(0).getIdClient());
            newRequest.setDeliveryAdress(payload.get("deliveryAddress").textValue());
            newRequest.setRequestValue(mathVariable);
            newRequest.setDataPedido(new Date());
        } else {
           throw new ClientNotFoundException(payload.get("clientName").textValue());
        }

        //Get new request ID
        Request savedRequest = repository.saveAndFlush(newRequest);

        //Save each ProductList under the new Request ID
        Iterator<ProductList> itProduct = productList.iterator();
        while (itProduct.hasNext()) {
            ProductList productListClaimer = itProduct.next();
            productListClaimer.setIdRequest(savedRequest.getIdRequest());
            productListRepository.save(productListClaimer);
        }

        //Send to RabbitMQ
        template.convertAndSend(RequestQueueConfig.EXCHANGE,RequestQueueConfig.ROUTING_KEY, savedRequest);

        //Returns
        return ResponseEntity //
                .created(linkTo(methodOn(RequestController.class).one(newRequest.getIdRequest())).toUri()) //
                .body(assembler.toModel(newRequest));
    }
}