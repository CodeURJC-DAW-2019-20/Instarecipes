package com.proyect.instarecipes.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.models.Comment;
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
public class IndexWebController {

    @Autowired
    private RecipesRepository recipesRepository;
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
        mixedIndex(model);
        return "index";
    }

    @GetMapping("/index")
    public String indexLogged(Model model) {
        mixedIndex(model);
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
    
    @PostMapping("/")
    public void postRecipe(Model model, Recipe recipe, @RequestParam MultipartFile imageFile, 
    @RequestParam String ingredientsString, @RequestParam String categoriesString,
    @RequestParam String cookingStyle, @RequestParam String allergen, 
    @RequestParam String firstStepString, @RequestParam(required = false) String stepsString, 
    @RequestParam(required = false, value = "allImages") MultipartFile[] allImages, 
    @RequestParam(required = false, value = "withImage") String withImage,HttpServletResponse response) throws IOException{
        
        Recipe r = indexService.postRecipe(recipe, ingredientsString, categoriesString, cookingStyle, allergen);
        recipesRepository.save(r);
        imageService.saveImage("recipes", r.getId(), imageFile);
        stepsRepository.save(new Step(r, 1, firstStepString));
        if(withImage.length()>0){
            String stp = withImage.substring(0, withImage.length()-1);
            List<String> listOfBools = Arrays.asList(stp.split(","));
            int i = 2;
            int j = 0;
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
        List<Recipe> recipes = indexService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        model.addAttribute("size", recipes.size());
        List<Recipe> subRecipe = recipes.subList(0, 3);
        model.addAttribute("subRecipe",subRecipe);
        List<Comment> comments = indexService.getRecipeComments(recipe);
        model.addAttribute("n_comments", comments.size());

        model.addAttribute("ingredientsList", profileService.getAllIngredients());
        model.addAttribute("cookingStylesList", profileService.getAllCookingStyles());
        model.addAttribute("categoriesList", profileService.getAllCategories());
        model.addAttribute("allergensList", profileService.getAllAllergens());
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
    
    private void mixedIndex(Model model){
        List<Recipe> recipes = indexService.getAllRecipes();
        model.addAttribute("size", recipes.size());
        model.addAttribute("recipes", recipes);
        model.addAttribute("ingredientsList", profileService.getAllIngredients());
        model.addAttribute("cookingStylesList", profileService.getAllCookingStyles());
        model.addAttribute("categoriesList", profileService.getAllCategories());
        model.addAttribute("allergensList", profileService.getAllAllergens());
        if(userSession.isLoggedUser()){
            model.addAttribute("filteredRecipe", indexService.getRecipesUserLogged(userSession.getLoggedUser().getId()));
            model.addAttribute("id", userSession.getLoggedUser().getId());
        }else{
            model.addAttribute("notTrending", indexService.getRecipesUserNotLogged());
        }
    }

}