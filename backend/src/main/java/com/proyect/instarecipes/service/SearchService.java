package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UsersRepository usersRepository;

    public ArrayList<String> getItem(String item) {
        return new ArrayList<String>(Arrays.asList(item.split(",")));
    }

    public List<Recipe> getFilteredRecipes(ArrayList<String> ing, ArrayList<String> cat, ArrayList<String> cS, ArrayList<String> al) {
        return recipesRepository.findFilteredSearch(ing, cat, cS, al);
    }

    // Better option filter db but so dificult
    public List<Category> restCategories(List<Category> all, ArrayList<String> categoriesSelected) {
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
    
    public List<CookingStyle> restCookingStyles(List<CookingStyle> all, ArrayList<String> cookingStylesSelected) {
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
    
    public List<Ingredient> restIngredients(List<Ingredient> all, ArrayList<String> ingredientsSelected) {
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
    
    public List<Allergen> restAllergens(List<Allergen> all, ArrayList<String> allergensSelected) {
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

    public List<User> getTrueUsers(String search){
        List<User> trueUsers = new ArrayList<User>();
        List<User> usersFounded = usersRepository.findAll();

        String userName = search.substring(1);
        
        for (int i=0; i < usersFounded.size(); i++) {
            String usernameUser = usersFounded.get(i).getUsername().toLowerCase(); //PUT THE TITLE WITH LOWER CASE
            boolean isCoincidence = usernameUser.contains(userName.toLowerCase()); //COMPARE THE WORDS WITH LOWER CASE
            if (isCoincidence) {
                trueUsers.add(usersFounded.get(i));
            }
        }
       
        return trueUsers;
    }

    public List<Recipe> getTrueRecipes(String search){
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
        return trueOnes;
    }

    private boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }

    private boolean isAlreadyIn (List<Recipe> recipeList, Long recipeId){
        boolean founded = false;

        for (int i=0; i< recipeList.size(); i++){
            if (recipeList.get(i).getId() == recipeId) {
                founded = true;
            }
        }

        return founded;
    }

    public List<Recipe> getAllRecipes(){
        return recipesRepository.findAll();
    }




}