package com.proyect.instarecipes.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.service.RankingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ranking")
public class RankingRestController{
    
    interface RankingData extends Recipe.Rankinglikes, User.Username{}

    @Autowired
    private RankingService rankingService;

    // AJAX PAGEABLE RANKING
    @JsonView(RankingRestController.RankingData.class)
    @GetMapping("/")
    public ResponseEntity<List<Recipe>> getRanking(){
        List<Recipe> newRanking=rankingService.showRanking();
        if (newRanking != null){
            return new ResponseEntity<>(newRanking, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}