package com.proyect.instarecipes.controllers;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.RecipesRepository;

import java.io.IOException;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class RecipeWebController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes/{id}")
    public String searchPage(Model model, @PathVariable Long id) {
        // User of the recipe
        User user = recipeService.getUserRecipe(id);
        model.addAttribute("user", user);
        // Number of publications(0) and total likes(1)
        List<Integer> likesAndComments = recipeService.getRecipeLikesAndPublications(user.getId());
        model.addAttribute("n_publications", likesAndComments.get(0));
        model.addAttribute("n_likes", likesAndComments.get(1));
        // Recipe
        Recipe recipe = recipeService.getRecipe(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("id", recipe.getId());
        // Number of all steps
        List<Step> steps = recipeService.getRecipeSteps(recipe.getId());
        model.addAttribute("n_steps", steps.size());
        model.addAttribute("steps", steps);
        // Comments
        List<Comment> comments = recipeService.getRecipeComments(recipe);
        model.addAttribute("n_comments", comments.size());
        if(userSession.isLoggedUser()){
            User actual = userSession.getLoggedUser();
            if(actual!=null){
                for(int i=0;i<comments.size();i++){ //algoritm to check if the actual use liked that comment
                    boolean aux = false;
                    for(User uAux : comments.get(i).getUsersLiked()){
                        if(uAux.getId() == actual.getId()){
                            aux = true;
                        }
                    }
                    comments.get(i).setLiked(aux);
                }
            }
            //Update LikeRecipe
            User userLogged = userSession.getLoggedUser(); 
            Recipe auxRecipe = recipesRepository.findRecipeById(id);
            Set<User> recipelikes = auxRecipe.getLikesUsers();
            boolean pressed= false;
            for(User u : recipelikes){
                if(u.getId()==userLogged.getId()){                
                    pressed = true;
                    break;
                }
            }
            model.addAttribute("pressed", pressed);
        }
        model.addAttribute("comments", comments);
        return "recipe";
    }

    @PostMapping("/postComment/{id}")
    public void postComment(@PathVariable Long id, Model model, @RequestParam String content, HttpServletResponse response,
            @RequestParam(required = false, value = "parentComment") Long parentComment) throws IOException {
        recipeService.postComment(id, content, parentComment, userSession.getLoggedUser());
        response.sendRedirect("../recipes/"+id);
    }

    @PostMapping("/actionUnpressLike/{id}")
    public void disLikedRecipe(Model model, @PathVariable Long id,HttpServletResponse response) throws IOException{
        User user = userSession.getLoggedUser();       
        recipeService.pressRecipeUnlike(id, user);
        response.sendRedirect("../recipes/"+id);
    }

    @PostMapping("/actionPressLike/{id}")
    public void likedRecipe(Model model, @PathVariable Long id,HttpServletResponse response)throws IOException{
        User user = userSession.getLoggedUser();       
        recipeService.pressRecipeLike(id, user);
        response.sendRedirect("../recipes/"+id);
    }
    
    @PostMapping("/likeComment/{id}")
    public void likeComment(@PathVariable Long id, @RequestParam Long id_recipe, HttpServletResponse response) throws IOException {
        User user = userSession.getLoggedUser();
        recipeService.likeComment(id, user);
        response.sendRedirect("../recipes/"+id_recipe);
    }

    @PostMapping("/unlikeComment/{id}")
    public void unlikeComment(@PathVariable Long id, @RequestParam Long id_recipe, HttpServletResponse response) throws IOException {
        User user = userSession.getLoggedUser();
        recipeService.unlikeComment(id, user);
        response.sendRedirect("../recipes/"+id_recipe);
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