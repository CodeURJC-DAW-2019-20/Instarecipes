package com.proyect.instarecipes.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.security.UserSession;
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
	@Autowired
	private UserSession userSession;
	
    @JsonView(SearchRestController.SearchInfo.class)
	@PostMapping("/filtered")
	public ResponseEntity<List<Recipe>> getFilteredRecipes(@RequestBody FilteredSearchDTO filteredSearchDTO) {

		ArrayList<String> cookingStylesSelected = searchService.getItem(filteredSearchDTO.getCookingStyles());
		ArrayList<String> categoriesSelected = searchService.getItem(filteredSearchDTO.getCategories());
        ArrayList<String> allergensSelected = searchService.getItem(filteredSearchDTO.getAllergens());
        ArrayList<String> ingredientsSelected = searchService.getItem(filteredSearchDTO.getIngredients());

		List<Recipe> recipesFounded = searchService.getFilteredRecipes(ingredientsSelected, categoriesSelected, cookingStylesSelected, allergensSelected);
		if (recipesFounded != null) {
			return new ResponseEntity<>(recipesFounded, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    @JsonView(SearchRestController.SearchInfo.class)
	@PostMapping("/navbar/users")
	public ResponseEntity<List<User>> getUserSearch(@RequestBody(required = false) String search){
		if(userSession.isLoggedUser()){
			String firstLetter = search.substring(0,1);
			if (firstLetter.equals("@")){
				List<User> trueUsers = searchService.getTrueUsers(search);
				return new ResponseEntity<>(trueUsers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	@JsonView(SearchRestController.SearchInfo.class)
	@PostMapping("/navbar/recipes") 
	public ResponseEntity<List<Recipe>> getRecipeSearch(@RequestBody(required = false) String search){
		if (search != null){
			List<Recipe> trueRecipes = searchService.getTrueRecipes(search);
			return new ResponseEntity<>(trueRecipes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}