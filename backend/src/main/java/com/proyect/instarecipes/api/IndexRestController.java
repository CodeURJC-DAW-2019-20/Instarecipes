package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.service.IndexService;

@RestController
@RequestMapping("/api")
public class IndexRestController{
	public interface Main extends User.Username, Recipe.RecipeBasic, Recipe.RecipePlus{}

    @Autowired
	private RecipesRepository recipesRepository;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private StepsRepository stepsRepository;
	@Autowired
	private IndexService indexService;

	//RECENT USERS PUBLICATIONS (AS ANNONYMOUS)
	@JsonView(IndexRestController.Main.class)
	@GetMapping("/")
	public ResponseEntity<List<Recipe>> getRecentUsersPublicationsNotLogged(){
        List<Recipe> allRecipes = indexService.getAllRecipes();
        if (allRecipes != null) {
			return new ResponseEntity<>(allRecipes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//RECENT USERS PUBLICATIONS (AS LOGGED)
	@JsonView(IndexRestController.Main.class)
	@GetMapping("/index")
	public ResponseEntity<List<Recipe>> getRecentUsersPublicationsLogged(@RequestParam Long id_user){
		List<Recipe> allRecipes = indexService.getAllRecipes();
        if (allRecipes != null) {
			return new ResponseEntity<>(allRecipes, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//TRENDING PUBLICATIONS (AS LOGGED)
	@JsonView(IndexRestController.Main.class)
	@GetMapping("/trending/logged")
	public ResponseEntity<List<Recipe>> getTrendingAlgorithmLogged(Long id_user){
		List<Recipe> trending = indexService.getRecipesUserLogged(id_user);
        if (trending != null) {
			return new ResponseEntity<>(trending, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//TRENDING PUBLICATIONS (AS ANNONYMOUS)
	@JsonView(IndexRestController.Main.class)
	@GetMapping("/trending/notLogged")
	public ResponseEntity<List<Recipe>> getTrendingAlgorithmNotLogged(){
		List<Recipe> trending = indexService.getRecipesUserNotLogged();
        if (trending != null) {
			return new ResponseEntity<>(trending, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// POSTING RECIPE (AS LOGGED) -> 
	// id_user, title, description, difficulty, duration, withImage, firstStepString,
	// ingredientsString, categoriesString, cookingStyle, allergen, stepsString
	@JsonView(IndexRestController.Main.class)
	@PostMapping("/index")
	public ResponseEntity<Recipe> postRecipe(@RequestParam Long id_user,
		@RequestParam String title, @RequestParam String description, 
		@RequestParam String difficulty, @RequestParam String duration, 
		@RequestParam(required = false) String withImage, @RequestParam String firstStepString,
    	@RequestParam String ingredientsString, @RequestParam String categoriesString,
    	@RequestParam String cookingStyle, @RequestParam String allergen, 
    	@RequestParam(required = false) String stepsString){
		
		Recipe recipe = new Recipe(usersRepository.findById(id_user).get() , null, null, null, null, title, description, duration, difficulty, null);
		Recipe r = indexService.postRecipe(recipe, ingredientsString, categoriesString, cookingStyle, allergen);
		recipesRepository.save(r);
		stepsRepository.save(new Step(r, 1, firstStepString));
		if(withImage.length()>0){
            // String stp = withImage.substring(0, withImage.length()-1);
            // List<String> listOfBools = Arrays.asList(stp.split(","));
            int i = 2;
            // int j = 0;
            if(stepsString != null){
                List<String> listOfSteps = Arrays.asList(stepsString.split("ab#12#45-3,"));
                for(String steps : listOfSteps){
                    if(steps != null){
                        Step step_n = new Step(r, i, steps);
                        // if(listOfBools.get(j).equalsIgnoreCase("1")){
                        //     imageService.saveImage("recipes/steps/"+r.getId(), j+2, allImages[j]);
                        //    step_n.setImage(true);
                        // }else{
                        //    step_n.setImage(false);
                        // }
                        stepsRepository.save(step_n);
                        // j++;
                        i++;
                    }
                }
            }
        }
        if (r != null) {
			return new ResponseEntity<>(r, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}