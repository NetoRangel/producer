package com.neto.producer.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void all() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/clients");
        MvcResult result = mvc.perform(request).andReturn();
    }


    @Test
    void one()  throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/clients/1");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("Amanda", result.getResponse().getContentAsString());
    }
    /*
    @Test
    void newClient() {
    }

    @Test
    void replaceClient() {
    }

    @Test
    void deleteClient() {
    }
    */
}