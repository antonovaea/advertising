package com.project.advertstand.service;

import com.project.advertstand.model.TopCategory;
import com.project.advertstand.model.TopCategoryStorage;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class TopCategoryService {

    @Inject
    TopCategoryStorage topCategoryStorage;

    public List<TopCategory> getTopCategoryStorage(){
        return topCategoryStorage.updateTopCategories();
    }

}
