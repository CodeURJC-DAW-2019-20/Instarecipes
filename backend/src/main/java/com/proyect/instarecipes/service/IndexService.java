package com.proyect.instarecipes.service;

import java.io.IOException;
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
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IndexService {
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
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private ImageService imageService;

    public List<Recipe> getAllRecipes() {
        // we create a list based on the database info!
        List<Recipe> recipes = recipesRepository.findAll();
        for (Recipe r : recipes) {
            r.setN_comments(commentsRepository.countByRecipeId(r));
        }
        recipesRepository.flush();
        return recipes;
    }

    public List<Comment> getRecipeComments(Recipe recipe) {
        return commentsRepository.findAllByRecipe(recipe);
    }

    public List<Recipe> personalFilter(User user) {
        List<Recipe> recipes = recipesRepository.FindByLikes();
        ArrayList<Recipe> filtered = new ArrayList<Recipe>(3); // list to show on index.html
        boolean logged = user != null;
        if (logged) {
            String myAllergen = user.getAllergens(); // allergen's user
            for (int pubs = 0; pubs < recipes.size(); pubs++) {
                Set<Allergen> ReciAllergens = recipes.get(pubs).getAllergens();
                boolean check = false;
                for (Allergen a : ReciAllergens) {
                    String allergen = a.getAllergen();
                    if (allergen.equalsIgnoreCase(myAllergen))
                        check = true;
                }
                if (!check)
                    filtered.add(recipes.get(pubs));
            }
            List<Recipe> filteredfinally = new ArrayList<>();
            int max = 3; // our max trending recipes
            for (int i = 0; i < max; i++) {
                filteredfinally.add(filtered.get(i));
            }
            return filteredfinally;
        } else {
            List<Recipe> xdList = recipesRepository.FindByLikes();
            List<Recipe> notLogged = new ArrayList<>();
            int maX = 3; // our max trending recipes
            for (int i = 0; i < maX; i++) {
                notLogged.add(xdList.get(i));
            }
            return notLogged;
        }
    }

    public List<Recipe> getRecentRecipesUserLogged(int page, int size) {
        List<Recipe> recipe = null;
        if(userSession.isLoggedUser()){
            User u = userSession.getLoggedUser();
            ArrayList<Long> fList = new ArrayList<>();
            for(User user : usersRepository.findFollowing(u.getUsername())){
                fList.add(user.getId());
            }
            recipe = recipesRepository.findAllRecipesByFollowing(fList, u.getId(), PageRequest.of(page, size)).getContent();
        }else{
            recipe = null;
        }
        return recipe;
    }

    public Recipe postRecipe(User user, Recipe recipe, String ingredientsString, String categoriesString,
            String cookingStyle, String allergen, String firstStepString, String stepsString, String withImage,
            MultipartFile imageFile, MultipartFile[] allImages) throws IOException {

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
        recipe.setUsername(user);
        recipesRepository.save(recipe);
        if(imageFile != null){
            imageService.saveImage("recipes", recipe.getId(), imageFile);
        }
        stepsRepository.save(new Step(recipe, 1, firstStepString));
        if(withImage.length()>0){
            String stp = withImage.substring(0, withImage.length()-1);
            List<String> listOfBools = Arrays.asList(stp.split(","));
            int i = 2;
            int j = 0;
            if(stepsString != null){
                List<String> listOfSteps = Arrays.asList(stepsString.split("ab_12_45_3,"));
                for(String steps : listOfSteps){
                    if(steps != null){
                        Step step_n = new Step(recipe, i, steps);
                        if(listOfBools.get(j).equalsIgnoreCase("1")){
                            if(allImages != null){
                               imageService.saveImage("recipes/steps/"+recipe.getId(), j+2, allImages[j]);
                                step_n.setImage(true);
                                step_n.setStepImage(allImages[j].getBytes()); 
                            }
                        }else{
                           step_n.setImage(false);
                        }
                        stepsRepository.save(step_n);
                        j++;
                        i++;
                    }
                }
            }
        }
        return recipe;
    }

    public byte[] postRecipeImages(User user, Long id_recipe, int n_step, MultipartFile image) throws IOException{
        Recipe recipe = recipesRepository.findById(id_recipe).get();
        if(recipesRepository.findUsernameByRecipeId(id_recipe).getId() == user.getId()){
            byte[] img = image.getBytes();
            if(n_step <= stepsRepository.findAll().size()){
                Step s = stepsRepository.findByRecipeIdAndNumber(id_recipe, n_step);
                if(n_step == 1){
                    System.out.println("hola tio que tal");
                    recipe.setMainImage(img);
                    imageService.saveImage("recipes", recipe.getId(), image);
                }else{
                    s.setStepImage(image.getBytes());
                    imageService.saveImage("recipes/steps/"+recipe.getId(), n_step, image);
                    s.setImage(true);
                }
                stepsRepository.flush();
                return img;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}