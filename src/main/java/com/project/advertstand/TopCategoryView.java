package com.project.advertstand;

import com.project.advertstand.model.TopCategory;
import com.project.advertstand.mq.Consumer;
import com.project.advertstand.service.TopCategoryService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Named
@ApplicationScoped
public class TopCategoryView {

    private final Consumer consumer;
    private final TopCategoryService topCategoryService;

    @Inject
    public TopCategoryView(Consumer consumer, TopCategoryService topCategoryService) {
        this.consumer = consumer;
        this.topCategoryService = topCategoryService;
    }

    @PostConstruct
    private void init() throws IOException, TimeoutException {
        consumer.consume();
    }

    @PreDestroy
    private void destroy() throws IOException, TimeoutException {
        consumer.stop();
    }

    public List<TopCategory> getTopCategories(){
        return topCategoryService.getTopCategoryStorage();
    }


}
