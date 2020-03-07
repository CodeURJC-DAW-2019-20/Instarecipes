package com.proyect.instarecipes.service;

import com.proyect.instarecipes.models.Request;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    public Request request(String ingredient, String category, String cookingStyle){
        Request rq = new Request();
        if(ingredient!=null)
            rq.setIngredientContent(ingredient);
        else if(category!=null)
            rq.setCategoryContent(category);
        else if(cookingStyle!=null)
            rq.setCookingStyleContent(cookingStyle);
        return rq;
    }

}