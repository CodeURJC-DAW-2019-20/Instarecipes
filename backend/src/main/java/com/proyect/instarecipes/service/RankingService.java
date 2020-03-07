package com.proyect.instarecipes.service;

import java.util.List;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RankingService{

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UserSession userSession;
    
    public List<Recipe> showRanking(){
        Page<Recipe> rankingPage = recipesRepository.findTopTen(PageRequest.of(0, 10));
        List<Recipe> ranking = rankingPage.getContent();
        return ranking;
    }

    public boolean islogged(){
        boolean logged = userSession.getLoggedUser() != null;
        return logged;
    }
    public String getUserLogged(){
        return userSession.getLoggedUser().getUsername();
    }
    public boolean isAdmin() {
        return userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN");
    }
}