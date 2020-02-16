//ARRAY datos ingredientes, categorias, cooking style and allergens

package com.proyect.instarecipes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.proyect.instarecipes.models.Recipe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class SearchPageController {
    private String ingredient;
    private String cookingStyle;
    /*private Set<String> ingredients;
    private Set<String> categories;
    private Set<String> cookingStyle;
    private Set<String> allergens;*/
    

    @PostMapping("/search")
    public String searchPage(Model model, String ingredients, String categories, String cookingStyles, String allergens) {
        cookingStyle = cookingStyles;
        if (cookingStyle.contentEquals("Vegan")) {
            model.addAttribute("cookingStyles", cookingStyles);
        }
        cookingStyle = null;
        System.out.print(" Ingrediente : "+ cookingStyles +".");
        return "search-page";
    }

    /*@GetMapping("/search")
    public String filterPage(Model model) {
            model.addAttribute("cookingStyles", cookingStyle);
        return "search-page";
    }*/
}
    /*@GetMapping("/search")
    public String filterPage(Model model) {
            model.addAttribute("ingredients", ingredient);
        return "search-page";
    }*/
