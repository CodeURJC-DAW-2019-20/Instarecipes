package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.IndexService;
import com.proyect.instarecipes.views.RecipeDTO;

@RestController
@RequestMapping("/api")
public class IndexRestController {
	public interface Main extends User.Username, Recipe.RecipeBasic, Recipe.RecipePlus {}
	public interface PostRecipe extends User.Username, RecipeDTO.PostRecipeView {}

	@Autowired
	private IndexService indexService;
	@Autowired
	private UserSession userSession;

	// RECENT USERS PUBLICATIONS (AS ANNONYMOUS)
	@JsonView(IndexRestController.Main.class)
	@GetMapping("/")
	public ResponseEntity<List<Recipe>> getRecentUsersPublicationsNotLogged() {
		List<Recipe> allRecipes = indexService.getAllRecipes();
		if (allRecipes != null) {
			return new ResponseEntity<>(allRecipes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// RECENT USERS PUBLICATIONS (AS LOGGED)
	@JsonView(IndexRestController.Main.class)
	@GetMapping("/index")
	public ResponseEntity<List<Recipe>> getRecentUsersPublicationsLogged(@RequestParam int page, @RequestParam int size) {
		List<Recipe> allRecipes = indexService.getRecentRecipesUserLogged(page, size);
		if (allRecipes != null) {
			return new ResponseEntity<>(allRecipes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// TRENDING PUBLICATIONS (AS LOGGED)
	@JsonView(IndexRestController.Main.class)
	@GetMapping("/trending")
	public ResponseEntity<List<Recipe>> getTrending() {
		List<Recipe> trending = null;
		if(userSession.isLoggedUser()){
			trending = indexService.personalFilter(userSession.getLoggedUser());
			if (trending != null) {
				return new ResponseEntity<>(trending, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}else{
			trending = indexService.personalFilter(null);
			if (trending != null) {
				return new ResponseEntity<>(trending, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	}

	// POSTING RECIPE (AS LOGGED) ->
	@JsonView(IndexRestController.PostRecipe.class)
	@PostMapping("/index")
	public ResponseEntity<RecipeDTO> postRecipe(@RequestBody RecipeDTO recipeDTO) throws IOException {
		if(userSession.isLoggedUser()){
			String withImage = "";
			for(String wI : recipeDTO.getWithImage()){
				withImage += wI+",";
			}
			String ingredientsString = "";
			for(String iS : recipeDTO.getIngredients()){
				ingredientsString += iS+",";
			}
			ingredientsString = ingredientsString.substring(0, ingredientsString.length()-1);
			String categoriesString = "";
			for(String c : recipeDTO.getCategories()){
				categoriesString += c+",";
			}
			categoriesString = categoriesString.substring(0, categoriesString.length()-1);
			String cookingStyle = "";
			for(String cS : recipeDTO.getCookingStyles()){
				cookingStyle += cS+",";
			}
			cookingStyle = cookingStyle.substring(0, cookingStyle.length()-1);
			String stepsString = "";
			for(String sS : recipeDTO.getSteps()){
				stepsString += sS+"ab_12_45_3,";
			}
			recipeDTO.setUser(userSession.getLoggedUser());
			Recipe recipe = new Recipe(null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), 
			recipeDTO.getTitle(), recipeDTO.getDescription(), recipeDTO.getDuration(), recipeDTO.getDifficulty(), new HashSet<>());
			Recipe r = indexService.postRecipe(userSession.getLoggedUser(),recipe, ingredientsString, categoriesString,
				cookingStyle, recipeDTO.getAllergen(), recipeDTO.getFirstStep(), stepsString, withImage, null, null);
			if (r != null) {
				return new ResponseEntity<>(recipeDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
		
	}

	// @JsonView(IndexRestController.PostRecipe.class)
	// @PostMapping("/index/{id}/image")
	// public ResponseEntity<Recipe> postRecipeImage(@PathVariable Long id, RecipeDTO recipeto) throws IOException {
		
	// 	// Recipe recipe = new Recipe(null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), title, description, duration, difficulty, new HashSet<>());
	// 	System.out.println(recipe);
	// 	Recipe r = indexService.postRecipe(userSession.getLoggedUser(),recipe, ingredientsString, categoriesString,
	// 		cookingStyle, allergen, firstStepString, stepsString, withImage,imageFile, allImages);
    //     if (r != null) {
	// 		return new ResponseEntity<>(r, HttpStatus.OK);
	// 	} else {
	// 		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// 	}
	// }

}