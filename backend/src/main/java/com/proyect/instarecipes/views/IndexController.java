package com.proyect.instarecipes.views;

import java.io.IOException;
import java.util.List;

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

@Controller
public class IndexController {

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private ImageService imageService;

    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private CookingStylesRepository cookingstylesRepository;
    @Autowired
    private AllergensRepository allergensRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;

    
    @GetMapping("/")
    public String indexPage(Model model) {
        //we create a list based on the database info!
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("size", recipes.size());
        List<Ingredient> ingredients = ingredientsRepository.findAll();
        List<CookingStyle> cookingStyles = cookingstylesRepository.findAll();
        List<Category> categories = categoriesRepository.findAll();
        List<Allergen> allergens = allergensRepository.findAll();

        //then we add the elements of the list in the model, AKA: we send them with mustache exampl: {{recipes}} 
        model.addAttribute("recipes", recipes);
        model.addAttribute("ingredientsList", ingredients);
        model.addAttribute("cookingStylesList", cookingStyles);
        model.addAttribute("categoriesList", categories);
        model.addAttribute("allergensList", allergens);

        return "index";
    }
    @GetMapping("/index")
    public String indexedPage(Model model) {
        List<Recipe> recipes = recipesRepository.findAll(); //should be sustituted by only following users publications
        model.addAttribute("size", recipes.size());
        model.addAttribute("recipes", recipes);
        return "index";
    }
    // @GetMapping("/ranking")
    // public String rankingPage() {
    //     return "ranking";
    // }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/signUp")
    public String signupPage(){
        return "signUp";
    }
    @GetMapping("/search")
    public String searchPage(){
        return "search-page";
    }
    @ModelAttribute
	public void addAttributes(Model model) {
		boolean logged = userSession.getLoggedUser() != null;
        model.addAttribute("logged", logged);
		if(logged){
			model.addAttribute("user",userSession.getLoggedUser().getUsername());
			model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		}
	}

    @PostMapping("/")
    public String postRecipe(Model model, Recipe recipe, @RequestParam MultipartFile imageFile) throws IOException{
        //recipes.add(recipe);
        System.out.println("IMAGEN: " + imageFile);
        Recipe r = recipe;
        r.setImage(true);
        recipesRepository.save(r);
        imageService.saveImage("recipes", r.getId(), imageFile);
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        List<Comment> comments = commentsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_comments", comments.size());
        return "index";
    }
}