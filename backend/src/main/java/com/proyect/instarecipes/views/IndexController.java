package com.proyect.instarecipes.views;

import java.util.List;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class IndexController {

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UserSession userSession;

    @GetMapping("/")
    public String indexPage(Model model) {
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }
    @GetMapping("/index")
    public String indexedPage(Model model) {
        List<Recipe> recipes = recipesRepository.findAll(); //should be sustituted by only following users publications
        model.addAttribute("recipes", recipes);
        return "index";
    }
    @GetMapping("/profile") 
    public String profilePage() {
        return "profile";
    }
    @GetMapping("/ranking")
    public String rankingPage() {
        return "ranking";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/signUp")
    public String signupPage(){
        return "signUp";
    }
    @GetMapping("/admin-profile")
    public String adminProfile() {
    	return "admin-profile";
    }

    @ModelAttribute
	public void addAttributes(Model model) {
		
		boolean logged = userSession.getLoggedUser() != null;
        model.addAttribute("logged", logged);
		//model.addAttribute("notLogged", !logged);
		
		if(logged){
			model.addAttribute("user",userSession.getLoggedUser().getUsername());
			model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		}
	}

    @PostMapping("/")
    public String postRecipe(Model model, Recipe recipe){
        //recipes.add(recipe);
        Recipe r = recipe;
        recipesRepository.save(r);
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }
}