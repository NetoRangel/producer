package com.neto.producer.rest;

import java.util.List;
import java.util.stream.Collectors;
import com.neto.producer.model.Client;
import com.neto.producer.repository.ClientRepository;
import com.neto.producer.util.ClientModelAssembler;
import com.neto.producer.util.ClientNotFoundException;
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
public class ClientController {

    private final ClientRepository repository;
    private final ClientModelAssembler assembler;

    ClientController(ClientRepository repository, ClientModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/clients")
    public CollectionModel<EntityModel<Client>> all() {

        List<EntityModel<Client>> client = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(client, linkTo(methodOn(ClientController.class).all()).withSelfRel());
    }

    @GetMapping("/clients/{id}")
    public EntityModel<Client> one(@PathVariable Long id) {

        Client client = repository.findById(id) //
                .orElseThrow(() -> new ClientNotFoundException(id));

        return assembler.toModel(client);
    }

    @PostMapping("/clients")
    public ResponseEntity<?> newClient(@RequestBody Client newClient) {

        EntityModel<Client> entityModel = assembler.toModel(repository.save(newClient));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<?> replaceClient(@RequestBody Client newClient, @PathVariable Long id) {

        Client updatedClient = repository.findById(id) //
                .map(client -> {
                    client.setIdClient(id);
                    client.setClientName(newClient.getClientName());
                    return repository.save(client);
                }) //
                .orElseGet(() -> {
                    newClient.setIdClient(id);
                    return repository.save(newClient);
                });

        EntityModel<Client> entityModel = assembler.toModel(updatedClient);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.ok().body("Client " + id + " deleted successfully");
        }
        else {
            return ResponseEntity //
                    .status(500).body("Client " + id + " not found");
        }
    }

}