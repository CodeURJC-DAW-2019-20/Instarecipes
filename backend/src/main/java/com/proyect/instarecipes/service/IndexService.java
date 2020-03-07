package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService{
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;
    @Autowired
    private AllergensRepository allergensRepository;

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private UserSession userSession;
    @Autowired 
    private UsersRepository usersRepository;

    public List<Recipe> getAllRecipes(){
        //we create a list based on the database info!
        List<Recipe> recipes = recipesRepository.findAll();
        for(Recipe r : recipes){
            r.setN_comments(commentsRepository.countByRecipeId(r));
        }
        recipesRepository.flush();
        return recipes;
    }

    public List<Recipe> getRecentRecipes(){
        return null;//para hacer
    }
    public List<Category> getSelectedCategories(){
        return null;//para hacer
    }
    public List<Ingredient> getSelectedIngredients(){
        return null;//para hacer
    }
    public List<Allergen> getSelectedAllergens(){
        return null;//para hacer
    }
    public List<CookingStyle> getSelectedCookingStyles(){
        return null;//para hacer
    }
    
    public List<Recipe> personalFilter(boolean logged, User user){
        List<Recipe> recipes = recipesRepository.FindByLikes();    
        ArrayList<Recipe> filtered = new ArrayList<Recipe>(3); //list to show on index.html
        if(logged){
            String myAllergen =user.getAllergens(); // allergen's user
            for(int pubs=0; pubs<recipes.size();pubs++){
                Set<Allergen> ReciAllergens = recipes.get(pubs).getAllergens();
                boolean check=false;
                for(Allergen a : ReciAllergens){
                    String allergen = a.getAllergen();
                    if(allergen.equalsIgnoreCase(myAllergen))
                        check = true;
                }
                if(!check)
                    filtered.add(recipes.get(pubs));     
            }
            List<Recipe> filteredfinally= new ArrayList<>();
            int max = 3; // our max trending recipes
            for(int i=0;i<max;i++){
                filteredfinally.add(filtered.get(i));
            }
            return filteredfinally;
        }
        else{
            List<Recipe> xdList= recipesRepository.FindByLikes();
            List<Recipe> notLogged = new ArrayList<>();
            int maX = 3; // our max trending recipes
            for(int i=0;i<maX;i++){
                notLogged.add(xdList.get(i));
            }
            return notLogged;
        }
    }

    public List<Recipe> getRecipesUserNotLogged () {
        boolean logged = userSession.isLoggedUser();
        List<Recipe> trending = null;
        if(logged){
            User user = userSession.getLoggedUser();
            trending = personalFilter(logged, user);
        }else{
            trending = personalFilter(logged, null);
        }
    return trending;
    }

    public List<Recipe> getRecipesUserLogged (String username) {
        User user = usersRepository.findByUsername(username);
        userSession.setLoggedUser(user);
        boolean logged = userSession.isLoggedUser();
        List<Recipe> trending = null;
        if(logged){
            //User user = userSession.getLoggedUser();
            trending = personalFilter(logged, user);
        }else{
            trending = personalFilter(logged, null);
        }
    return trending;
    }

}