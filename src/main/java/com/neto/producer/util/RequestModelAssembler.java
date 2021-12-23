package com.neto.producer.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.neto.producer.model.Request;
import com.neto.producer.rest.RequestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RequestModelAssembler implements RepresentationModelAssembler<Request, EntityModel<Request>> {

    @Override
    public EntityModel<Request> toModel(Request request) {

        return EntityModel.of(request, //
                linkTo(methodOn(RequestController.class).one(request.getIdRequest())).withSelfRel(),
                linkTo(methodOn(RequestController.class).all()).withRel("requests"));
    }
}