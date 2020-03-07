package com.proyect.instarecipes.controllers;

import java.util.ArrayList;
import java.util.List;

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.ProfileService;
import com.proyect.instarecipes.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

@Controller
public class SearchPageController {

    @Autowired
    private UserSession userSession;
    @Autowired
    private SearchService searchService;
    @Autowired 
    private ProfileService profileService;

    @PostMapping("/search")
    public String searchPage(Model model, @RequestParam(required = false) String ingredients, 
    @RequestParam(required = false) String categories, @RequestParam(required = false) 
    String cookingStyles, @RequestParam(required = false) String allergens) {
                
        model.addAttribute("searchByButton", true);
        ArrayList<String> cookingStylesSelected = searchService.getItem(cookingStyles);
        if (cookingStylesSelected.get(0) != "") { // fix the bug when only 1 is selected :)
            model.addAttribute("cookingStyles", cookingStylesSelected);
        }
        ArrayList<String> categoriesSelected = searchService.getItem(categories);
        if (categoriesSelected.get(0) != "") {
            model.addAttribute("categories", categoriesSelected);
        }
        ArrayList<String> allergensSelected = searchService.getItem(allergens);
        if (allergensSelected.get(0) != "") {
            model.addAttribute("allergens", allergensSelected);
        }
        ArrayList<String> ingredientsSelected = searchService.getItem(ingredients);
        if (ingredientsSelected.get(0) != "") {
            model.addAttribute("ingredients", ingredientsSelected);
        }

        List<Category> restOfCategories = searchService.restCategories(profileService.getAllCategories(), categoriesSelected);
        List<CookingStyle> restOfCookingStyles = searchService.restCookingStyles(profileService.getAllCookingStyles(), cookingStylesSelected);
        List<Ingredient> restOfIngredients = searchService.restIngredients(profileService.getAllIngredients(), ingredientsSelected);
        List<Allergen> restOfAllergens = searchService.restAllergens(profileService.getAllAllergens(), allergensSelected);

        // Return all the items
        model.addAttribute("allCategories", restOfCategories);
        model.addAttribute("allCookingStyles", restOfCookingStyles);
        model.addAttribute("allIngredients", restOfIngredients);
        model.addAttribute("allAllergens", restOfAllergens);

        // Search by items
        List<Recipe> recipesFounded = searchService.getFilteredRecipes(ingredientsSelected, categoriesSelected, cookingStylesSelected, allergensSelected);
        if(recipesFounded.size()==0){
            model.addAttribute("notFound", true);
        }else{
            model.addAttribute("notFound", false);
            model.addAttribute("recipesFound", recipesFounded);
        }

        return "search";
    }

    @PostMapping("/searchBar")
    public String searchBarPage(Model model, @RequestParam(required = false) String search) {
        if(search == null || search == ""){
            model.addAttribute("nothingFound", true);
        }else{
            model.addAttribute("nothingFound", false);    
            model.addAttribute("searchText", search);
            String firstLetter = search.substring(0,1);

            if (firstLetter.equals("@")){
                //IF THE USER SEARCH USERS
                List<User> trueUsers = searchService.getTrueUsers(search);

                if(trueUsers.isEmpty()){
                    model.addAttribute("notUsersFound", true);
                }else{
                    model.addAttribute("notUsersFound", false);
                    model.addAttribute("usersFound", trueUsers);
                }

                // IF THE USER SEARCH RECIPES:
            } else {
                model.addAttribute("searchText", search);
                model.addAttribute("notUsersFound", false);
                //SEARCHED VALUES TO A LIST       
                
                List<Recipe> trueRecipes = searchService.getTrueRecipes(search);
                if(trueRecipes.size()==0){
                    model.addAttribute("notFound", true);
                }else{
                    model.addAttribute("notFound", false);
                    model.addAttribute("recipesFound", trueRecipes);
                }
            }
        }
        model.addAttribute("searchByButton", false);
        return "search";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        boolean logged = userSession.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        if (logged) {
            model.addAttribute("user", userSession.getLoggedUser().getUsername());
            model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
        }
    }
}