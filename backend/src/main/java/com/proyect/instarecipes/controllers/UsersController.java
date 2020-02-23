package com.proyect.instarecipes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersController{
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UserSession userSession;

    @GetMapping("/users/{id}")
    public ModelAndView anotherUserProfile(@PathVariable Long id){
        ModelAndView model = new ModelAndView("profile");
        Optional<User> actual =  usersRepository.findById(id);
        //User
        model.addObject("actualUser", actual.get());
        model.addObject("logged", userSession.isLoggedUser());
        if(userSession.isLoggedUser() && actual.get().getRoles().contains("ROLE_ADMIN")){
            model.addObject("admin", true);
        }else{
            model.addObject("user", true);
        }
        //All users
        model.addObject("usersList", usersRepository.findAll());
        //Number of followers
        model.addObject("n_followers", usersRepository.findFollowers(actual.get().getUsername()).size());
        //Number of following
        model.addObject("n_following", usersRepository.findFollowing(actual.get().getUsername()).size());
        //Followers
        model.addObject("followers", usersRepository.findFollowers(actual.get().getUsername()));
        //Following
        model.addObject("following", usersRepository.findFollowing(actual.get().getUsername()));
        //Number of publications and total likes
        List<Recipe> recipes = recipesRepository.findByUsernameId(id);
        ArrayList<Integer> Laiks = new ArrayList<Integer>();
        ArrayList<String> titles = new ArrayList<String>();
        int likes = 0;
        int pubs;
        
        for(pubs=0; pubs<recipes.size();pubs++){
            likes = likes + recipes.get(pubs).getLikes();
            Laiks.add(recipes.get(pubs).getLikes()); //List of every user recipe LIKES!!
            titles.add(recipes.get(pubs).getTitle());
        }
        System.out.println(Laiks);
        System.out.println(titles);
        
        model.addObject("n_publications", pubs);
        model.addObject("n_likes", likes);
        //Publications
        model.addObject("publications", recipes);
        model.addObject("idkwtp", Laiks);
        model.addObject("likesGraphics", titles);
        if(userSession.getLoggedUser().getId() != id){
            model.addObject("followButton", true);
        }else{
            model.addObject("followButton", false);
        }
        return model;
    }
}