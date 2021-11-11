package com.project.advertstand.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TopCategoryStorage implements Serializable {

    private List<TopCategory> topCategories = new ArrayList<>();

    public TopCategoryStorage() {
    }

    public TopCategoryStorage(List<TopCategory> topCategories) {
        this.topCategories = topCategories;
    }

    public List<TopCategory> getTopCategories() {
        return topCategories;
    }

    public void setTopCategories(List<TopCategory> topCategories) {
        this.topCategories = topCategories;
    }

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
    private final String URL_GET_OCCASION_BY_REST = "http://localhost:8080/home/advertising/top";
    private Client client = new Client();

    public List<TopCategory> updateTopCategories(){
        WebResource webResource = client.resource(URL_GET_OCCASION_BY_REST);
        String response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class).getEntity(String.class);
        List<TopCategory> list = null;
        try {
            list = objectMapper.readValue(response, new TypeReference<List<TopCategory>>() {

            });
        } catch (IOException e){
            System.out.println("error to load data" + e.getMessage());
        }
        return list;

    }

    @PostConstruct
    public void init() {
        updateTopCategories();
    }

}
