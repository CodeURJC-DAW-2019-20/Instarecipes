package com.proyect.instarecipes.service;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

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
import com.proyect.instarecipes.security.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;
    @Autowired
    private AllergensRepository allergensRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ImageService imageService;
    
    public List<User> getAllUser(){
        return usersRepository.findAll(); 
    }
    public String getName(Long id){
        return usersRepository.findById(id).get().getName();
    }

    public String getSurName(Long id){
        return usersRepository.findById(id).get().getSurname();
    }

    public String getInfo(Long id){
        return usersRepository.findById(id).get().getInfo();
    }

    public int getFollowersCount(String username){
        return usersRepository.findFollowers(username).size(); 
    }

    public String getAllergen(Long id){
        return usersRepository.findById(id).get().getAllergens();
    }

    public int getFollowingCount(String username){
        return usersRepository.findFollowing(username).size(); 
    }

    public List<User> getAllFollowers(String username){
        return usersRepository.findFollowers(username); 
    }

    public List<User> getAllFollowing(String username){
        return usersRepository.findFollowing(username); 
    }

    public List<Integer> getUserRecipeDetails(Long id){
        List<Recipe> recipes = recipesRepository.findByUsernameId(id);
        int likes = 0;
        int pubs;
        for (pubs = 0; pubs < recipes.size(); pubs++) {
            likes = likes + recipes.get(pubs).getLikes();          
        }   
        List<Integer> recipeDetails = new ArrayList<>();
        recipeDetails.add(pubs);
        recipeDetails.add(likes);      
        
        return recipeDetails;
    }

    public List<Allergen> getAllAllergens(){
        return allergensRepository.findAll();
    }

    public List<Category> getAllCategories(){
        return categoriesRepository.findAll();
    }

    public List<Ingredient> getAllIngredients(){
        return ingredientsRepository.findAll();
    }

    public List<CookingStyle> getAllCookingStyles(){
        return cookingStylesRepository.findAll();
    }

    public User updateUser(User user, MultipartFile avatarFile, MultipartFile backgroundFile, String name, String surname, String allergens, String info)throws IOException{
        user.setName(name);
        user.setSurname(surname);
        user.setAllergens(allergens);
        user.setInfo(info);
        usersRepository.flush();
        if(!avatarFile.isEmpty()){
            imageService.saveImage("avatars", user.getId(), avatarFile);
        }
        if(!backgroundFile.isEmpty()){
            imageService.saveImage("backgrounds", user.getId(), backgroundFile);
        }
        return user;
    }
    public ArrayList<Integer> getLaiks(List<Recipe> recipes){
        ArrayList<Integer> Laiks = new ArrayList<Integer>();
        for (int i = 0; i < recipes.size(); i++) {
            Laiks.add(recipes.get(i).getLikes()); 
        }
        return Laiks;
    }

    public ArrayList<String> getTitles(List<Recipe> recipes){
        ArrayList<String> titles = new ArrayList<String>();
        for (int i = 0; i < recipes.size(); i++) {
            titles.add(recipes.get(i).getTitle()); 
        }
        return titles;
    }

    public List<Recipe> getByUsernameId(Long id){
        return recipesRepository.findByUsernameId(id);
    }
}

