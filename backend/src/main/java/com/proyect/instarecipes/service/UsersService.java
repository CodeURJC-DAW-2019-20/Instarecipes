package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RequestsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RequestsRepository requestsRepository;

    public Optional<User> getActualUser(Long id) {
        return usersRepository.findById(id);
    }

    public ArrayList<Integer> getRecipesLikes(List<Recipe> recipes) {
        ArrayList<Integer> Laiks = new ArrayList<Integer>();
        for (int pubs = 0; pubs < recipes.size(); pubs++) {
            Laiks.add(recipes.get(pubs).getLikes());
        }
        return Laiks;
    }

    public ArrayList<String> getRecipesTitles(List<Recipe> recipes) {
        ArrayList<String> Titles = new ArrayList<String>();
        for (int pubs = 0; pubs < recipes.size(); pubs++) {
            Titles.add(recipes.get(pubs).getTitle());
        }
        return Titles;
    }

    public int getAllPubsLikes(List<Recipe> recipes) {
        int likes = 0;
        for (int pubs = 0; pubs < recipes.size(); pubs++) {
            likes = likes + recipes.get(pubs).getLikes();
        }
        return likes;
    }

    public List<User> getFollowingUsers(User u){
        return usersRepository.findFollowing(u.getUsername());
    }
    
    public boolean getIsFollowing(List<User> following, Long id){
        boolean isFollowing = false;
        for (User user : following) {
            if (user.getId() != id) {
                isFollowing = false;
            } else {
                isFollowing = true;
                break;
            }
        }
        return isFollowing;
    }
    
    public boolean getDisable(User u, Long id){
        boolean disable = false;
        if (u.getId() == id) {
            disable = false;
        } else {
            disable = true;
        }
        return disable;
    }

    public List<Request> getRequestList() {
        return requestsRepository.findAll();
    }
}