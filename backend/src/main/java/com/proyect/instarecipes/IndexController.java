package com.proyect.instarecipes;

import java.util.ArrayList;
import java.util.List;

import com.proyect.instarecipes.recipe.Recipe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class IndexController {
    private List<Recipe> recipes = new ArrayList<>();

	public IndexController() {
        recipes.add(new Recipe(null, "@boss99", 111, "This smoothie is made from fresh ingredients. It's sweet and creamy, I love it and I hope you do too. Only with a few steps you will have the perfect summer drink! ", "XXXX", "YYYY", "ejemplo titulo1", "ejemplo descripcion1", "15 min.", "Hard", null));
        recipes.add(new Recipe(null, "@lady44", 222, "Strawberry banana smoothie! (Easy & Healthy)", "XXXX", "YYYY", "ejemplo titulo2", "ejemplo descripcion2", "15 min.", "Hard", null));
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("recipes", recipes);
        return "index";
    }
    @GetMapping("/profile") 
    public String profilePage(Model model) {
        return "profile";
    }
    @GetMapping("/ranking")
    public String rankingPage(Model model) {
        return "ranking";
    }
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping("/")
    public String postRecipe(Model model, Recipe recipe) {

        recipes.add(recipe);
        model.addAttribute("recipes", recipes);

        return "index"; 
    }
    
}