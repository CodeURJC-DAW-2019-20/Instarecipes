package com.proyect.instarecipes.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.CookingStyle;

import com.proyect.instarecipes.service.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchRestController {

	public interface SearchInfo extends Recipe.RecipeBasic, Recipe.RecipeView, Recipe.RecipeACS, User.Username, User.NameSurname,
	 					Ingredient.Item, Category.Item, Allergen.Item, CookingStyle.Item{}

	@Autowired
	private SearchService searchService;

	
    @JsonView(SearchRestController.SearchInfo.class)
	@PostMapping("/filtered")
	public ResponseEntity<List<Recipe>> getFilteredRecipes(@RequestParam(required = false) String ingredients, 
    		@RequestParam(required = false) String categories, @RequestParam(required = false) 
    			String cookingStyles, @RequestParam(required = false) String allergens) {

		ArrayList<String> cookingStylesSelected = searchService.getItem(cookingStyles);
		ArrayList<String> categoriesSelected = searchService.getItem(categories);
        ArrayList<String> allergensSelected = searchService.getItem(allergens);
        ArrayList<String> ingredientsSelected = searchService.getItem(ingredients);

		// List<Category> restOfCategories = searchService.restCategories(profileService.getAllCategories(), categoriesSelected);
        // List<CookingStyle> restOfCookingStyles = searchService.restCookingStyles(profileService.getAllCookingStyles(), cookingStylesSelected);
        // List<Ingredient> restOfIngredients = searchService.restIngredients(profileService.getAllIngredients(), ingredientsSelected);
        // List<Allergen> restOfAllergens = searchService.restAllergens(profileService.getAllAllergens(), allergensSelected);

		List<Recipe> recipesFounded = searchService.getFilteredRecipes(ingredientsSelected, categoriesSelected, cookingStylesSelected, allergensSelected);
		if (recipesFounded != null) {
			return new ResponseEntity<>(recipesFounded, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


    @JsonView(SearchRestController.SearchInfo.class)
	@PostMapping("/navbar/users")
	public ResponseEntity<List<User>> getUserSearch(@RequestParam(required = false) String search){
		String firstLetter = search.substring(0,1);
		if (firstLetter.equals("@")){
			List<User> trueUsers = searchService.getTrueUsers(search);
			return new ResponseEntity<>(trueUsers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(SearchRestController.SearchInfo.class)
	@PostMapping("/navbar/recipes") 
	public ResponseEntity<List<Recipe>> getRecipeSearch(@RequestParam(required = false) String search){
		String firstLetter = search.substring(0,1);
		if (!firstLetter.equals("@")){
			List<Recipe> trueRecipes = searchService.getTrueRecipes(search);
			return new ResponseEntity<>(trueRecipes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}