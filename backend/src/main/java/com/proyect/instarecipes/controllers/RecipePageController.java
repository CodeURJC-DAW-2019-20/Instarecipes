package com.proyect.instarecipes.controllers;

import java.util.List;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

@Controller
public class RecipePageController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private StepsRepository stepsRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @GetMapping("/recipes/{id}")
    public String searchPage(Model model, @PathVariable Long id) {
        User user = recipesRepository.findUsernameByRecipeId(id);
        System.out.println("USUARIO: " + user.getUsername());
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
        Recipe recipe = recipesRepository.findRecipeById(id);
        model.addAttribute("recipe", recipe);
        List<Step> steps = stepsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_steps", steps.size());
        model.addAttribute("steps", steps);
        List<Comment> comments = commentsRepository.findAllByRecipe(recipe);
        model.addAttribute("comments", comments);
        return "recipe";
    }

    @ModelAttribute
	public void addAttributes(Model model) {
		boolean logged = userSession.getLoggedUser() != null;
        model.addAttribute("logged", logged);
		if(logged){
			model.addAttribute("user",userSession.getLoggedUser().getUsername());
			model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		}
	}
}