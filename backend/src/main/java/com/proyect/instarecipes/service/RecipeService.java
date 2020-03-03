package com.proyect.instarecipes.service;

import com.proyect.instarecipes.repositories.RecipesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class RecipeService{

    @Autowired
    private RecipesRepository recipesRepository;
    

}