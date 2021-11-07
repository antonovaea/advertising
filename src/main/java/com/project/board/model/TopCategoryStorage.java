package com.project.board.model;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.core.MediaType;
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

    public void updateTopCategories() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource("http://localhost:8080/home/advertising/top");
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        List list = response.getEntity(ArrayList.class);
    }

    @PostConstruct
    public void init() {
        updateTopCategories();
    }

}
