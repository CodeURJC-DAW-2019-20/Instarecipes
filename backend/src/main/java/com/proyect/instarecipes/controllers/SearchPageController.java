package com.proyect.instarecipes.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.classic.pattern.ThreadConverter;

import org.springframework.ui.Model;

@Controller
public class SearchPageController {

    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;
    @Autowired
    private AllergensRepository allergensRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/search")
    public String searchPage(Model model, String ingredients, String categories, String cookingStyles,
            String allergens) {
                
        model.addAttribute("searchByButton", true);
        ArrayList<String> cookingStylesSelected = new ArrayList<String>(Arrays.asList(cookingStyles.split(",")));
        if (cookingStylesSelected.get(0) != "") { // fix the bug when only 1 is selected :)
            model.addAttribute("cookingStyles", cookingStylesSelected);
        }
        ArrayList<String> categoriesSelected = new ArrayList<String>(Arrays.asList(categories.split(",")));
        if (categoriesSelected.get(0) != "") {
            model.addAttribute("categories", categoriesSelected);
        }
        ArrayList<String> allergensSelected = new ArrayList<String>(Arrays.asList(allergens.split(",")));
        if (allergensSelected.get(0) != "") {
            model.addAttribute("allergens", allergensSelected);
        }
        ArrayList<String> ingredientsSelected = new ArrayList<String>(Arrays.asList(ingredients.split(",")));
        System.out.println("LOS INGREDIENTES: "+ ingredientsSelected);
        if (ingredientsSelected.get(0) != "") {
            model.addAttribute("ingredients", ingredientsSelected);
            
        }

        List<Category> allCategories = categoriesRepository.findAll();
        List<Category> restOfCategories = restCategories(allCategories, categoriesSelected);

        List<CookingStyle> allCookingStyles = cookingStylesRepository.findAll();
        List<CookingStyle> restOfCookingStyles = restCookingStyles(allCookingStyles, cookingStylesSelected);

        List<Ingredient> allIngredients = ingredientsRepository.findAll();
        List<Ingredient> restOfIngredients = restIngredients(allIngredients, ingredientsSelected);

        List<Allergen> allAllergens = allergensRepository.findAll();
        List<Allergen> restOfAllergens = restAllergens(allAllergens, allergensSelected);

        // Return all the items
        model.addAttribute("allCategories", restOfCategories);
        model.addAttribute("allCookingStyles", restOfCookingStyles);
        model.addAttribute("allIngredients", restOfIngredients);
        model.addAttribute("allAllergens", restOfAllergens);

        // Search by items
        List<Recipe> recipesFounded = recipesRepository.findFilteredSearch(ingredientsSelected, categoriesSelected, allergensSelected, cookingStylesSelected);
        System.out.println("LAS RECETAS: "+ recipesFounded.toString());
        if(recipesFounded.size()==0){
            model.addAttribute("notFound", true);
        }else{
            model.addAttribute("notFound", false);
            model.addAttribute("recipesFound", recipesFounded);
        }

        return "search";
    }

    // Better option filter db but so dificult
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
    
    private List<Ingredient> restIngredients(List<Ingredient> all, ArrayList<String> ingredientsSelected) {
        List<Ingredient> aux = all;
        for (int i = 0; i < ingredientsSelected.size(); i++) {
            for (int j = 0; j < all.size(); j++) {
                if (ingredientsSelected.contains(all.get(j).getIngredient())) {
                    aux.remove(j);
                }
            }
        }
        return aux;
    }
    
    private List<Allergen> restAllergens(List<Allergen> all, ArrayList<String> allergensSelected) {
        List<Allergen> aux = all;
        for (int i = 0; i < allergensSelected.size(); i++) {
            for (int j = 0; j < all.size(); j++) {
                if (allergensSelected.contains(all.get(j).getAllergen())) {
                    aux.remove(j);
                }
            }
        }
        return aux;
    }
      
    @PostMapping("/searchBar")
    public String searchBarPage(Model model, @RequestParam String search) {
    String firstLetter = search.substring(0,1);
        model.addAttribute("searchText", search);

    if (firstLetter.equals("@")){
        //IF THE USER SEARCH USERS
        List<User> usersFounded = usersRepository.findAll();
        List<User> trueUsers = new ArrayList<User>();

        String userName = search.substring(1);
        
        for (int i=0; i < usersFounded.size(); i++) {
            String usernameUser = usersFounded.get(i).getUsername().toLowerCase(); //PUT THE TITLE WITH LOWER CASE
            boolean isCoincidence = usernameUser.contains(userName.toLowerCase()); //COMPARE THE WORDS WITH LOWER CASE
            if (isCoincidence) {
                trueUsers.add(usersFounded.get(i));
            }
        }

        if(usersFounded.size()==0){
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
        String[] words = search.split(" ");
        List<String> filteredWords = new ArrayList<String>();
        String[] quitWords = {"with","and","or","without", "in", "to", "&", "for", "on", "minutes", "min"};

        for (int i=0; i<words.length;i++){
            //words[i].toLowerCase().contains("with")
            if (!stringContainsItemFromList(words[i].toLowerCase(),quitWords)) {
                filteredWords.add(words[i]);
            }
            
        }

        List<Recipe> recipesFounded2 = recipesRepository.findAll();
        List<Recipe> trueOnes = new ArrayList<Recipe>(); 

        for (int i=0; i < filteredWords.size(); i++){
            String palabrita = filteredWords.get(i);
            for (int j=0; j < recipesFounded2.size(); j++) {
                String titleRecipe = recipesFounded2.get(j).getTitle().toLowerCase().replaceAll("[^a-zA-Z]"," "); //PUT THE TITLE WITHOUT SPECIAL CHARACTERS AND LOWER CASE
                boolean isCoincidence = titleRecipe.contains(palabrita.toLowerCase().replaceAll("[^a-zA-Z]"," ")); //COMPARE THE WORDS WITHOUT SPECIAL CHARACTERS AND LOWER CASE
           
                if ((isCoincidence) && (!isAlreadyIn(trueOnes, recipesFounded2.get(j).getId()))) {
                    trueOnes.add(recipesFounded2.get(j));
                }
            }
        }

        if(trueOnes.size()==0){
            model.addAttribute("notFound", true);
        }else{
            model.addAttribute("notFound", false);
            model.addAttribute("recipesFound", trueOnes);
        }

    }
        model.addAttribute("searchByButton", false);
        return "search";
    }

    public static boolean isAlreadyIn (List<Recipe> recipeList, Long recipeId){
        boolean founded = false;

        for (int i=0; i< recipeList.size(); i++){
            if (recipeList.get(i).getId() == recipeId) {
                System.out.println("ya esta dentro bro");
                founded = true;
            }
        }

        return founded;
    }

    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }
}