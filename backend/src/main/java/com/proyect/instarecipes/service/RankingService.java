package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingService{

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserSession userSession;
    
    public List<Recipe> showRanking(){
        Page<Recipe> rankingPage = recipesRepository.findTopTen(PageRequest.of(0, 10));
        List<Recipe> ranking = rankingPage.getContent();
        return ranking;
    }

    

    public List<Integer> getRecipeLikesAndPublications(Long id){
        List<Recipe> recipes = recipesRepository.findByUsernameId(id);
        int n_likes = 0;
        int n_publications;
        for (n_publications = 0; n_publications < recipes.size(); n_publications++) {
            n_likes = n_likes + recipes.get(n_publications).getLikes();
        }
        List<Integer> array = new ArrayList<>();
        array.add(n_likes); // 1st position -> likes
        array.add(n_publications); // 1st position -> likes
        return array;
    }

    public List<Step> getRecipeSteps(Recipe recipe){
        return stepsRepository.findAllByRecipe(recipe);
    }

    public Recipe pressRecipeUnlike(Long id_recipe, User user){
        Recipe recipe = recipesRepository.findRecipeById(id_recipe);
        Set<User> recipeLikes = recipe.getLikesUsers();
        for(User u : recipeLikes){
            if(u.getId() == user.getId()){
                recipe.removeUser(u);           
                recipesRepository.flush();
                break;
            }
        }
        return recipe;
    }


}