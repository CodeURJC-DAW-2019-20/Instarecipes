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
        recipes.add(new Recipe(null, "@boss99", 111, "??? ", "¿¿¿¿", "Homemade Pizza!", "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!", 
        "ejemplo descripcion2", "15 min.", "Hard", null));
        recipes.add(new Recipe(null, "@lady44", 222, "???", "¿¿¿¿", "Avocado Salad", "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese, and an avocado…toss it alltogether, and done. It’s summery, healthy, and so good!", 
        "ejemplo descripcion2", "15 min.", "Hard", null));
    }

    @GetMapping("/index")
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

    @PostMapping("/index")
    public String postRecipe(Model model, Recipe recipe) {

        recipes.add(recipe);
        model.addAttribute("recipes", recipes);

        return "index"; 
    }
    @GetMapping("/search-page")
    public String searchPage(Model model) {
        return "search-page";
    }
}