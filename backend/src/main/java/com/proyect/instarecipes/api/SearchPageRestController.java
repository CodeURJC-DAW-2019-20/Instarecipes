package com.proyect.instarecipes.api;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.CookingStyle;

import com.proyect.instarecipes.repositories.RecipesRepository;

@RestController
@RequestMapping("/api/search")
public class SearchPageRestController {

	public interface SearchInfo extends Recipe.RecipeBasic, Recipe.RecipeView, Recipe.RecipeACS, User.Username, User.NameSurname,
	 Ingredient.Item, Category.Item, Allergen.Item, CookingStyle.Item{}

    
    @Autowired
	private RecipesRepository recipesRepository;
	
    @JsonView(SearchPageRestController.SearchInfo.class)
	@GetMapping("/")
	public ResponseEntity<Recipe> getItem(@RequestParam long id) {

		Optional<Recipe> recipe = recipesRepository.findById(id);

		if (recipe.get() != null) {
			return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}


	}
}