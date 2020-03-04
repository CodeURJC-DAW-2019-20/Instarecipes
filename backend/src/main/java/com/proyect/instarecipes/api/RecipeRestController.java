package com.proyect.instarecipes.api;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController{
    public interface SimpleRecipe extends Recipe.RecipeView, 
        Recipe.IndexView, Ingredient.Item, Category.Item, User.Username, User.NameSurname{}

    @Autowired
    private RecipesRepository recipesRepository;

    @JsonView(RecipeRestController.SimpleRecipe.class)
    @GetMapping("/{id}")
   public ResponseEntity<Recipe> getItem(@PathVariable long id){
       Optional<Recipe> recipe = recipesRepository.findById(id);
       if (recipe.get() != null){
           return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
       } else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }

}