
package com.proyect.instarecipes.controllers;

import java.util.List;

import com.proyect.instarecipes.models.Recipe;

import com.proyect.instarecipes.repositories.RecipesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipesController{

    @Autowired
    private RecipesRepository recipesRepository;

    @GetMapping("/recipes")
    public List<Recipe> getRecipes(Model model, @RequestParam("page") int page_number, @RequestParam("size") int page_size){
        Page<Recipe> recipes = recipesRepository.findAllRecipes(PageRequest.of(page_number,page_size));
        List<Recipe> recipeList = (List<Recipe>)recipes.getContent();
        return recipeList;
    } 
}