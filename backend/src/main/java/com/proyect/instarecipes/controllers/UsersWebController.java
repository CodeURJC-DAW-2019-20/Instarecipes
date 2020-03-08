package com.proyect.instarecipes.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.ProfileService;
import com.proyect.instarecipes.service.UsersService;

import java.io.Console;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersWebController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/users/{id}")
    public ModelAndView anotherUserProfile(@PathVariable Long id, HttpServletResponse response) throws IOException{
        ModelAndView model = null;
        if(!userSession.isLoggedUser()){
            response.sendRedirect("/login");
        }else{
            model = new ModelAndView("profile");
            Optional<User> actual = usersService.getActualUser(id);
            // User
            model.addObject("actualUser", actual.get());
            model.addObject("logged", userSession.isLoggedUser());
            if (userSession.isLoggedUser() && actual.get().getRoles().contains("ROLE_ADMIN")) {
                model.addObject("admin", true);
            } else {
                model.addObject("user", true);
            }
            // All users
            model.addObject("usersList", usersRepository.findAll());
            // Number of followers
            model.addObject("n_followers", usersRepository.findFollowers(actual.get().getUsername()).size());
            // Number of following
            model.addObject("n_following", usersRepository.findFollowing(actual.get().getUsername()).size());
            // Followers
            model.addObject("followers", usersRepository.findFollowers(actual.get().getUsername()));
            // Following
            model.addObject("following", usersRepository.findFollowing(actual.get().getUsername()));
            // Number of publications and total likes
            List<Recipe> recipes = profileService.getByUsernameId(id);

            model.addObject("ingredientsList", profileService.getAllIngredients());
            model.addObject("cookingStylesList", profileService.getAllCookingStyles());
            model.addObject("categoriesList", profileService.getAllCategories());

            model.addObject("n_publications", recipes.size());
            model.addObject("n_likes", usersService.getAllPubsLikes(recipes));
            // Publications
            model.addObject("publications", recipes);
            model.addObject("idkwtp", usersService.getRecipesLikes(recipes));
            model.addObject("likesGraphics", usersService.getRecipesTitles(recipes));

            User u = userSession.getLoggedUser();
            List<User> following = usersService.getFollowingUsers(u);

            boolean disable = usersService.getDisable(u, id);
            if (userSession.getLoggedUser().getId() != id) {
                model.addObject("followButton", usersService.getIsFollowing(following, id));
            }
           
            model.addObject("disable", disable);
        }
        return model;

    }

    @ModelAttribute
    public void addAttributes(Model model) {
        boolean logged = userSession.getLoggedUser() != null;
        model.addAttribute("logged", logged);
        if (logged) {
            model.addAttribute("user", userSession.getLoggedUser().getUsername());
            model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
        }
        List<Request> requestsList = usersService.getRequestList();
        model.addAttribute("allRequests", requestsList);
    }

    @PostMapping("/followAction/{id}")
    public void followAction(@PathVariable Long id, Model model, HttpServletResponse response)throws IOException {
        usersService.pressFollow(id);
        response.sendRedirect("../users/"+id); 
    }

    Console console = System.console();

    @PostMapping("/unfollowAction/{id}")
    public void unfollowAction(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {
        usersService.pressUnfollow(id);
        response.sendRedirect("../users/"+id);  
    }
    
}