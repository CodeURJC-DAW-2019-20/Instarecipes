package com.proyect.instarecipes.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Recipe;
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
    private UserSession userSession;
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
        
        indexService.postRecipe(userSession.getLoggedUser(), recipe, ingredientsString, categoriesString,
            cookingStyle, allergen, firstStepString, stepsString, withImage,imageFile, allImages);

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
            model.addAttribute("filteredRecipe", indexService.personalFilter(userSession.getLoggedUser()));
            model.addAttribute("id", userSession.getLoggedUser().getId());
        }else{
            model.addAttribute("notTrending", indexService.personalFilter(null));
        }
    }

}