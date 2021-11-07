package com.project.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.board.model.TopCategory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Named
@ApplicationScoped
public class TopCategoryService {

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
    private final String URL_GET_OCCASION_BY_REST = "http://localhost:8080/home/advertising/top";
    private Client client = new Client();

    public List<TopCategory> getTopCategoryStorage(){
        WebResource webResource = client.resource(URL_GET_OCCASION_BY_REST);
        String response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class).getEntity(String.class);
        List<TopCategory> list = null;
        try {
            list = objectMapper.readValue(response, new TypeReference<List<TopCategory>>() {

            });
        } catch (IOException e){
            System.out.println("error" + e.getMessage());
        }
        return list;

    }

}
