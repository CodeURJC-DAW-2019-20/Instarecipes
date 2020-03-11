package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.IndexService;
import com.proyect.instarecipes.views.DTO.RecipeDTO;

@RestController
@RequestMapping("/api")
public class IndexRestController {
	public interface Main extends User.Username, Recipe.RecipeBasic, Recipe.RecipePlus {}
	public interface PostRecipe extends User.Username, RecipeDTO.PostRecipeView {}

	@Autowired
	private IndexService indexService;
	@Autowired
	private UserSession userSession;
	@Autowired
	private StepsRepository stepsRepository;
	@Autowired
	private RecipesRepository recipesRepository;

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

	// POSTING RECIPE (AS LOGGED)
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

	// POST AN IMAGE OF ONE STEP OF ONE RECIPE
	@JsonView(IndexRestController.PostRecipe.class)
	@PostMapping(value = "/index/{id}/image/{step}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> postRecipeImage(@PathVariable Long id, @PathVariable int step, 
	@RequestParam MultipartFile imageFile)throws IOException {
		if(userSession.isLoggedUser()){
			byte[] image = indexService.postRecipeImages(userSession.getLoggedUser(), id, step, imageFile);
			if(image != null){
				return new ResponseEntity<>(image, HttpStatus.OK);
			}else{
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	@GetMapping(value = "/index/{id}/image/{step}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getRecipeMainImage(@PathVariable Long id,@PathVariable int step) throws IOException {
        Optional<Recipe> recipe = recipesRepository.findById(id);
        int num;
        if(step==1){
             num=0;
        }else{
            num=1;
        }
        if (!recipe.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Recipe recipenew = recipe.get();
            switch (num) {
                case 0:
					File file = new File("temp/recipes/image-"+recipenew.getId()+".jpg");
                    return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
                case 1:
                    Step s = stepsRepository.findByRecipeIdAndNumber(recipenew.getId(),step);
					if(s!=null){
						byte[]  imagestep = s.getStepImage();
						return new ResponseEntity<>(imagestep, HttpStatus.OK);
					}else{
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
                default:
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }       
    }

}