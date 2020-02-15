package com.proyect.instarecipes.views;

import java.util.ArrayList;
import java.util.List;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
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

    private List<Recipe> recipes = new ArrayList<>();

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
        List<Recipe> recipes = recipesRepository.findAll();
        //in the future: empty when he first time loged in, cause the query will be the recipes of his followers
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
    @GetMapping("/search-page")
    public String searchPage() {
        return "search-page";
    }
    @GetMapping("/loginerror")
    public String loginerror() {
    	return "loginerror";
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
			model.addAttribute("username",userSession.getLoggedUser().getName());
			model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		}
	}

    @PostMapping("/")
    public String postRecipe(Model model, Recipe recipe) {
        //recipes.add(recipe);
        Recipe r = recipe;
        recipesRepository.save(r);
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    User u = new User();

    @PostMapping("/signUp")
    public String loginPage(Model model, User user){
        u = user;
        return "signUp";
    }
}