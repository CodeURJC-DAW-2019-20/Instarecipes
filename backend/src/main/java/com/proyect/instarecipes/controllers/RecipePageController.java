<<<<<<< HEAD
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.models.ImageService;

@Controller
public class RecipePageController{

    @Autowired
    private RecipesRepository recipeRep;
    @Autowired
    private ImageService ImageService;


    @GetMapping("/recipes/show")
	public String Allrecipes(Model model) {

		List<Recipe> recipes = recipeRep.findAll();
		
		model.addAttribute("recetas", recipes);

		return "index";
	}

    @GetMapping("/recipe/new")
	public String NewRecipeForm() {
		return "index";
	}

	@PostMapping("/anuncio/guardar")
	public String NewRecipe(Model model, Recipe recipe, @RequestParam MultipartFile imagenFile) throws IOException {

		recipe.setImagen(true);
		
		repository.save(recipe);
		
		imgService.saveImage("receta", recipe.getId(), imagenFile);

		return "index";

	}

	@GetMapping("/anuncio/{id}")
	public String verRecipe(Model model, @PathVariable long id) {

		Optional<Recipe> recipe = repository.findById(id);
		if(recipe.isPresent()) {
			model.addAttribute("receta", recipe.get());
		}
		
		return "Simple-recipe";
	}






=======
package com.proyect.instarecipes.controllers;

import java.util.List;
import java.util.Optional;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class RecipePageController {
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @GetMapping("/recipes/{id}")
    public String searchPage(Model model, @PathVariable Long id) {
        User user = recipesRepository.findUsernameByRecipeId(id);
        System.out.println("USUARIO: " + user.getUsername());
        model.addAttribute("user", user);
        // Number of publications and total likes
        List<Recipe> recipes = recipesRepository.findByUsernameId(user.getId());
        int likes = 0;
        int pubs;
        for (pubs = 0; pubs < recipes.size(); pubs++) {
            likes = likes + recipes.get(pubs).getLikes();
        }
        model.addAttribute("n_publications", pubs);
        model.addAttribute("n_likes", likes); 
        Recipe recipe = recipesRepository.findRecipeById(id);
        model.addAttribute("recipe", recipe);
        List<Step> steps = stepsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_steps", steps.size());
        model.addAttribute("steps", steps);
        List<Comment> comments = commentsRepository.findAllByRecipe(recipe);
        model.addAttribute("comments", comments);
        return "recipe";
    }
>>>>>>> e3d02640d95219d5aef0cf2e1d6653059ce0e393
}