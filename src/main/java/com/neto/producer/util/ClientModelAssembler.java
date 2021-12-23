package com.neto.producer.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.neto.producer.model.Client;
import com.neto.producer.rest.ClientController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClientModelAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {

    @Override
    public EntityModel<Client> toModel(Client client) {

        return EntityModel.of(client, //
                linkTo(methodOn(ClientController.class).one(client.getIdClient())).withSelfRel(),
                linkTo(methodOn(ClientController.class).all()).withRel("clients"));
    }
}