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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


}