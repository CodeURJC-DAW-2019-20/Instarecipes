package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
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

    public List<Comment> getRecipeComments(Recipe recipe){
        return commentsRepository.findAllByRecipe(recipe);
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
    
    public List<Recipe> personalFilter(User user){
        List<Recipe> recipes = recipesRepository.FindByLikes();    
        ArrayList<Recipe> filtered = new ArrayList<Recipe>(3); //list to show on index.html
        boolean logged = user != null;
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
        return personalFilter(null);
    }

    public List<Recipe> getRecipesUserLogged(Long id_user) {
        Optional<User> user = usersRepository.findById(id_user);
        return personalFilter(user.get());
    }

    public Recipe postRecipe(Recipe recipe, String ingredientsString, String categoriesString, String cookingStyle, String allergen){

        // Ingredients selector //
        List<String> listOfIngs = Arrays.asList(ingredientsString.split(","));
        Set<Ingredient> lastIngs = new HashSet<>();
        for(String ings : listOfIngs){
            Optional<Ingredient> ingredient = ingredientsRepository.findByIngredient(ings);
            if(ingredient != null){
                lastIngs.add(ingredient.get());
            }
        }
        recipe.setIngredients(lastIngs);
        // Categories selector //
        List<String> listOfCats = Arrays.asList(categoriesString.split(","));
        Set<Category> lastCats = new HashSet<>();
        for(String cats : listOfCats){
            Optional<Category> category = categoriesRepository.findByCategory(cats);
            if(category.isPresent()){
                lastCats.add(category.get());
            }
        }
        recipe.setCategories(lastCats);
        if(allergen != "-- Select --"){
            Set<Allergen> all = allergensRepository.findByName(allergen);
            recipe.setAllergens(all);
        }
        if(cookingStyle != "-- Select --"){
            Set<CookingStyle> cStyle = cookingStylesRepository.findByName(cookingStyle);
            recipe.setCookingStyles(cStyle);
        }
        //Sets in recipe
        recipe.setImage(true);
        recipe.setUsername(userSession.getLoggedUser());
        return recipe;
    }

}