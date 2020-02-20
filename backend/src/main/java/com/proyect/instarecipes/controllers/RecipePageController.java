package com.proyect.instarecipes.controllers;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.RecipesRepository;

import java.util.List;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.StepsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

@Controller
public class RecipePageController {

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @GetMapping("/recipes/{id}")
    public String searchPage(Model model, @PathVariable Long id) {

        // User of the recipe
        User user = recipesRepository.findUsernameByRecipeId(id);
        model.addAttribute("user", user);

        // Number of publications and total likes
        List<Recipe> recipes = recipesRepository.findByUsernameId(user.getId());
        int likes = 0;
        int pubs;
        for (pubs = 0; pubs < recipes.size(); pubs++) {
            likes = likes + recipes.get(pubs).getLikes();
        }
        model.addAttribute("n_publications", pubs);
        model.addAttribute("n_likes", likes);

        // // Recipe
        Recipe recipe = recipesRepository.findRecipeById(id);
        model.addAttribute("recipe", recipe);

        // Number of all steps
        List<Step> steps = stepsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_steps", steps.size());
        model.addAttribute("steps", steps);

        // Comments
        List<Comment> comments = commentsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_comments", comments.size());
        model.addAttribute("comments", comments);
        return "recipe";
    }
}