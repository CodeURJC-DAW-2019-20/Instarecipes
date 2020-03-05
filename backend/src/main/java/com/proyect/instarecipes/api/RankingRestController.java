package com.proyect.instarecipes.api;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Recipe.likesRanking;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.service.RankingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ranking")
public class RankingRestController{

    @Autowired
    private RankingService rankingService;

    interface RankingData extends Recipe.likesRanking, User.Username{}

    @JsonView(RankingData.class)
    @GetMapping("/")
    public ResponseEntity<Recipe> getRanking(@RequestParam long page){
        List<Recipe> newRanking=rankingService.showRanking();
        for(Recipe recipe: newRanking):
            if (recipe != null){
                return new ResponseEntity<>(recipe, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
}