
package com.proyect.instarecipes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipesController{

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserSession userSession;

    @GetMapping("/recipes")
    public List<Recipe> getRecipes(Model model, @RequestParam("page") int page_number, @RequestParam("size") int page_size){
        Page<Recipe> recipes = null;
        if(userSession.isLoggedUser()){
            User user = userSession.getLoggedUser();
            Set<User> fList = usersRepository.findFollowingSet(user.getUsername());
            ArrayList<Long> list1 = new ArrayList<>();
            for(User u : fList){
                list1.add(u.getId());
            }
            // System.out.println("Set: " + list1);
            recipes = recipesRepository.findAllRecipesByFollowing(list1,PageRequest.of(page_number,page_size));
        }else{
            recipes = recipesRepository.findAllRecipes(PageRequest.of(page_number,page_size));
        }

        List<Recipe> recipeList = (List<Recipe>)recipes.getContent();
        return recipeList;
    } 
}