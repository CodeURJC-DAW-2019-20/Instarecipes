package com.proyect.instarecipes.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.IndexService;
import com.proyect.instarecipes.service.ProfileService;

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
    private CookingStylesRepository cookingStylesRepository;
    @Autowired
    private AllergensRepository allergensRepository;

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private ImageService imageService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private ProfileService profileService;

    
    @GetMapping("/")
    public String indexNotLogged(Model model) {
        List<Recipe> recipes = indexService.getAllRecipes();
        model.addAttribute("size", recipes.size());
        model.addAttribute("recipes", recipes);
    
        List<Recipe> trending = indexService.getRecipesUserNotLogged();
        model.addAttribute("filteredRecipe", trending);
        model.addAttribute("notTrending", trending);
        
        model.addAttribute("ingredientsList", profileService.getAllIngredients());
        model.addAttribute("cookingStylesList", profileService.getAllCookingStyles());
        model.addAttribute("categoriesList", profileService.getAllCategories());
        model.addAttribute("allergensList", profileService.getAllAllergens());
        if(userSession.isLoggedUser()){
            model.addAttribute("id", userSession.getLoggedUser().getId());
        }
        return "index";
    }

    @GetMapping("/index")
    public String indexLogged(Model model) {
        List<Recipe> recipes = indexService.getAllRecipes();
        model.addAttribute("size", recipes.size());
        model.addAttribute("recipes", recipes);
        
        List<Recipe> trending = indexService.getRecipesUserNotLogged();

        model.addAttribute("filteredRecipe", trending);
        model.addAttribute("notTrending", trending);
        
        model.addAttribute("ingredientsList", profileService.getAllIngredients());
        model.addAttribute("cookingStylesList", profileService.getAllCookingStyles());
        model.addAttribute("categoriesList", profileService.getAllCategories());
        model.addAttribute("allergensList", profileService.getAllAllergens());
        model.addAttribute("id", userSession.getLoggedUser().getId());
        
        return "index";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public String signupPage(Model model){
        model.addAttribute("allergensSignUp", profileService.getAllAllergens());
        return "signUp";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "search";
    }
    
    //para hacer
    @PostMapping("/")
    public void postRecipe(Model model, Recipe recipe, @RequestParam MultipartFile imageFile, 
    @RequestParam String ingredientsString, @RequestParam String categoriesString,
    @RequestParam String cookingStyle, @RequestParam String allergen, 
    @RequestParam String firstStepString, @RequestParam(required = false) String stepsString, 
    @RequestParam(required = false, value = "allImages") MultipartFile[] allImages, 
    @RequestParam(required = false, value = "withImage") String withImage,HttpServletResponse response) throws IOException{
        
        Recipe r = recipe;
        int i = 2;

// Ingredients selector //
        List<String> listOfIngs = Arrays.asList(ingredientsString.split(","));
        Set<Ingredient> lastIngs = new HashSet<Ingredient>();
        for(String ings : listOfIngs){
            Optional<Ingredient> ingredient = ingredientsRepository.findByIngredient(ings);
            if(ingredient != null){
                lastIngs.add(ingredient.get());
            }
        }
// Categories selector //
        List<String> listOfCats = Arrays.asList(categoriesString.split(","));
        Set<Category> lastCats = new HashSet<Category>();
        for(String cats : listOfCats){
            Optional<Category> category = categoriesRepository.findByCategory(cats);
            if(category.isPresent()){
                lastCats.add(category.get());
            }
        }
        if(allergen != "-- Select --"){
            Set<Allergen> all = allergensRepository.findByName(allergen);
            r.setAllergens(all);
        }
        if(cookingStyle != "-- Select --"){
            Set<CookingStyle> cStyle = cookingStylesRepository.findByName(cookingStyle);
            r.setCookingStyles(cStyle);
        }
//Sets in recipe
        r.setCategories(lastCats);
        r.setIngredients(lastIngs);

        r.setImage(true);
        r.setUsername(userSession.getLoggedUser());
        recipesRepository.save(r);
        imageService.saveImage("recipes", r.getId(), imageFile);
//Steps selector
        int j = 0;
        stepsRepository.save(new Step(r, 1, firstStepString));
        if(withImage.length()>0){
            String stp = withImage.substring(0, withImage.length()-1);
            List<String> listOfBools = Arrays.asList(stp.split(","));

            if(stepsString != null){
                List<String> listOfSteps = Arrays.asList(stepsString.split("ab#12#45-3,"));
                for(String steps : listOfSteps){
                    if(steps != null){
                        Step step_n = new Step(r, i, steps);
                        if(listOfBools.get(j).equalsIgnoreCase("1")){
                            imageService.saveImage("recipes/steps/"+r.getId(), j+2, allImages[j]);
                           step_n.setImage(true);
                        }else{
                           step_n.setImage(false);
                        }
                        stepsRepository.save(step_n);
                        j++;
                        i++;
                    }
                }
            }
        }
//SHOWING ALL ELEMENTS TO MUSTACHE
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        model.addAttribute("size", recipes.size());
        List<Recipe> subRecipe = recipes.subList(0, 3);
        model.addAttribute("subRecipe",subRecipe);
        List<Comment> comments = commentsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_comments", comments.size());

        List<Ingredient> allIngredients = ingredientsRepository.findAll();
        List<CookingStyle> allCookingStyles = cookingStylesRepository.findAll();
        List<Category> allCategories = categoriesRepository.findAll();
        List<Allergen> allAllergens = allergensRepository.findAll();

        model.addAttribute("ingredientsList", allIngredients);
        model.addAttribute("cookingStylesList", allCookingStyles);
        model.addAttribute("categoriesList", allCategories);
        model.addAttribute("allergensList", allAllergens);
        model.addAttribute("id", userSession.getLoggedUser().getId());

        response.sendRedirect("index");
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


}