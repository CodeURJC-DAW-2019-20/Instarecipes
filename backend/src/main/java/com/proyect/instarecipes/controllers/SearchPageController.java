package com.proyect.instarecipes.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class SearchPageController {

    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;
    @Autowired
    private RecipesRepository recipesRepository;

    @PostMapping("/search")
    public String searchPage(Model model, String ingredients, String categories, String cookingStyles, String allergens) {

        ArrayList<String> cookingStylesSelected = new ArrayList<String>(Arrays.asList(cookingStyles.split(",")));
        if(cookingStylesSelected.get(0) != ""){ //fix the bug when only 1 is selected :) 
            model.addAttribute("cookingStyles", cookingStylesSelected);
        }
        ArrayList<String> categoriesSelected = new ArrayList<String>(Arrays.asList(categories.split(",")));
        if(categoriesSelected.get(0) != ""){
            model.addAttribute("categories", categoriesSelected);
        }
        ArrayList<String> allergensSelected = new ArrayList<String>(Arrays.asList(allergens.split(",")));
        if(allergensSelected.get(0) != ""){
            model.addAttribute("allergens", allergensSelected);
        }
            ArrayList<String> ingredientsSelected = new ArrayList<String>(Arrays.asList(ingredients.split(",")));
        if(ingredientsSelected.get(0) != ""){
            model.addAttribute("ingredients", ingredientsSelected);
        }

        List<Category> allCategories = categoriesRepository.findAll();
        List<Category> restOfCategories = restCategories(allCategories, categoriesSelected);
        List<CookingStyle> allCookingStyles = cookingStylesRepository.findAll();
        List<CookingStyle> restOfCookingStyles = restCookingStyles(allCookingStyles, cookingStylesSelected);

        //Return all the items
        model.addAttribute("allCategories", restOfCategories);
        model.addAttribute("allCookingStyles", restOfCookingStyles);

        //Search by items
        // List<Recipe> recipesFounded = recipesRepository.findAll();
        List<Recipe> recipesFounded = recipesRepository.findFilteredSearch(ingredientsSelected,categoriesSelected,allergensSelected,cookingStylesSelected);
        System.out.println("LISTA: " + recipesFounded);
        model.addAttribute("recipesFound", recipesFounded);

        return "search";
    }

    //Better option filter db but so dificult
    private List<Category> restCategories(List<Category> all, ArrayList<String> categoriesSelected) {
        List<Category> aux = all;
        for (int i = 0; i < categoriesSelected.size(); i++) {
            for (int j = 0; j < all.size(); j++) {
                if (categoriesSelected.contains(all.get(j).getCategory())) {
                    aux.remove(j);
                }
            }
        }
        return aux;
    }
    private List<CookingStyle> restCookingStyles(List<CookingStyle> all, ArrayList<String> cookingStylesSelected) {
        List<CookingStyle> aux = all;
        for (int i = 0; i < cookingStylesSelected.size(); i++) {
            for (int j = 0; j < all.size(); j++) {
                if (cookingStylesSelected.contains(all.get(j).getCookingStyle())) {
                    aux.remove(j);
                }
            }
        }
        return aux;
    }

}