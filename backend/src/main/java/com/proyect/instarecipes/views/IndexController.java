package com.proyect.instarecipes.views;

import java.io.IOException;
import java.util.List;

import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.models.Recipe;
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
    private UserSession userSession;
    @Autowired
    private ImageService imageService;

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
    public String postRecipe(Model model, Recipe recipe, @RequestParam MultipartFile imageFile) throws IOException{
        //recipes.add(recipe);
        System.out.println("IMAGEN: " + imageFile);
        Recipe r = recipe;
        r.setImage(true);
        recipesRepository.save(r);
        imageService.saveImage("recipes", r.getId(), imageFile);
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }
}