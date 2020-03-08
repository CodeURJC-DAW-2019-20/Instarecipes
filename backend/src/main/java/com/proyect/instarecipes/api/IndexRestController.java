package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.IndexService;

@RestController
@RequestMapping("/api")
public class IndexRestController {
	public interface Main extends User.Username, Recipe.RecipeBasic, Recipe.RecipePlus {}
	public interface PostRecipe extends User.Username, Recipe.RecipeBasic, Recipe.RecipePlus, Step.StepsView {}

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
	// id_user, title, description, difficulty, duration, withImage,
	// firstStepString,
	// ingredientsString, categoriesString, cookingStyle, allergen, stepsString
	@JsonView(IndexRestController.PostRecipe.class)
	@PostMapping("/index")
	public ResponseEntity<Recipe> postRecipe(@RequestParam String title,
			@RequestParam String description, @RequestParam String difficulty, @RequestParam String duration,
			@RequestParam(required = false) String withImage, @RequestParam String firstStepString,
			@RequestParam String ingredientsString, @RequestParam(required = false) String categoriesString,
			@RequestParam(required = false) String cookingStyle, @RequestParam(required = false) String allergen,
			@RequestParam(required = false) String stepsString, @RequestParam(required = false) MultipartFile imageFile,
			@RequestParam(required = false) MultipartFile[] allImages)
			throws IOException {
		
		Recipe recipe = new Recipe(null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), title, description, duration, difficulty, new HashSet<>());
		System.out.println(recipe);
		Recipe r = indexService.postRecipe(userSession.getLoggedUser(),recipe, ingredientsString, categoriesString,
			cookingStyle, allergen, firstStepString, stepsString, withImage,imageFile, allImages);
        if (r != null) {
			return new ResponseEntity<>(r, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}