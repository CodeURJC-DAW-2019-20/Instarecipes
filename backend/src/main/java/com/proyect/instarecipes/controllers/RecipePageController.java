package com.proyect.instarecipes.controllers;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.RecipesRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.views.GroupStaff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        model.addAttribute("id", recipe.getId());

        // Number of all steps
        List<Step> steps = stepsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_steps", steps.size());
        model.addAttribute("steps", steps);

        // Comments
        List<Comment> comments = commentsRepository.findAllByRecipe(recipe);
        model.addAttribute("n_comments", comments.size());
        model.addAttribute("comments", comments);

        //Update LikeRecipe
        User userLogged = userSession.getLoggedUser(); 
        Recipe auxRecipe = recipesRepository.findRecipeById(id);
        Set<User> recipelikes = auxRecipe.getLikesUsers();
        boolean pressed= false;
        System.out.println("press is: "+pressed);
        for(User u : recipelikes){
            if(u.getId()==userLogged.getId()){                
                pressed = true;
                break;
            }
        }
        System.out.println("press is: "+pressed);
        model.addAttribute("pressed", pressed);

        return "recipe";

        
    }

    @PostMapping("/postComment/{id}")
    public void postComment(@PathVariable Long id, Model model, @RequestParam String content, HttpServletResponse response,
            @RequestParam(required = false, value = "parentComment") Long parentComment) throws IOException {
        
        GroupStaff groupStaff = new GroupStaff();
        Optional<Comment> pComment = null; //The parent comment
        Set<Comment> subComment = null; //The subcomment
        Comment comment = null;

        User u = userSession.getLoggedUser();
        Recipe r = recipesRepository.findRecipeById(id);
        
        if(parentComment != null){
            pComment = commentsRepository.findById(parentComment);
        }
        if((content != null)){
            if(pComment == null){
                comment = new Comment(u, content, null, 0, r, false, false);
            }else{
                subComment = groupStaff.groupComments(pComment.get());
                comment = new Comment(u, content, subComment, 0, r, false, true);
            }
            if(content != ""){
                commentsRepository.save(comment);
            }
        }
        response.sendRedirect("../recipes/"+id);
    }
    @PostMapping("/actionUnpressLike/{id}")
    public void disLikedRecipe(Model model, @PathVariable Long id,HttpServletResponse response)throws IOException{
        
        User user = userSession.getLoggedUser();       
        Recipe recipe = recipesRepository.findRecipeById(id);
        Set<User> recipeLikes = recipe.getLikesUsers();
        System.out.println("tamaño joder "+ recipeLikes.size());
        System.out.println("Mi nombre es "+ user.getUsername());      
        for(User u : recipeLikes){
            System.out.println("yo soy "+user.getId()+ " y tu eres "+ u.getId());
            if(u.getId() == user.getId()){
                System.out.println("testigo "+recipe.getLikesUsers().size());
                System.out.println("testigo likes "+recipe.getLikes());
                recipe.removeUser(u);           
                System.out.println("el real "+recipe.getLikesUsers().size());
                System.out.println("likes "+recipe.getLikes());
                recipesRepository.flush();
                break;
            }
        }
        response.sendRedirect("../recipes/"+id);
    }

    @PostMapping("/actionPressLike/{id}")
    public void likedRecipe(Model model, @PathVariable Long id,HttpServletResponse response)throws IOException{
        
        User user = userSession.getLoggedUser();       
        Recipe recipe = recipesRepository.findRecipeById(id);
        Set<User> recipeLikes = recipe.getLikesUsers();
        boolean check=false;
        System.out.println("Mi nombre es "+ user.getUsername()); 
        System.out.println("tamaño de "+ recipeLikes.size());
        for(User u : recipeLikes){
            if(u.getId()==user.getId()){              
                check= true;
                break;
            }
        }
        if(!check){
            System.out.println("likes antes "+recipe.getLikes());
            recipe.addUser(user);
            recipesRepository.flush();
            System.out.println("likes despues "+recipe.getLikes());
        }
        response.sendRedirect("../recipes/"+id);
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