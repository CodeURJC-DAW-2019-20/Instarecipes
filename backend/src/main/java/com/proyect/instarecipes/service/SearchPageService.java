package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.List;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.RecipesRepository;

import org.springframework.beans.factory.annotation.Autowired;


public class SearchPageService {
    @Autowired 
    private RecipesRepository recipesRepository;
    
    public List<Recipe> getAllRecipes(){
        List<Recipe> recipesFounded2 = recipesRepository.findAll();
        return recipesFounded2;
    }

    public void getAllRecipesFiltered() {
    List<Recipe> recipesFiltered = new ArrayList<>();

    }
}