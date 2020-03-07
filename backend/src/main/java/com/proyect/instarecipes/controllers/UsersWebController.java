package com.proyect.instarecipes.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.RequestsRepository;
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
    private RecipesRepository recipesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private RequestsRepository requestsRepository;
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
            
            ArrayList<Integer> Laiks = usersService.getRecipesLikes(recipes);
            ArrayList<String> titles = usersService.getRecipesTitles(recipes);

            int likes = usersService.getAllPubsLikes(recipes);
            int pubs = recipes.size();

            List<Category> catList = profileService.getAllCategories();
            List<Ingredient> ingList = profileService.getAllIngredients();
            List<CookingStyle> cSList = profileService.getAllCookingStyles();

            model.addObject("ingredientsList", ingList);
            model.addObject("cookingStylesList", cSList);
            model.addObject("categoriesList", catList);

            model.addObject("n_publications", pubs);
            model.addObject("n_likes", likes);
            // Publications
            model.addObject("publications", recipes);
            model.addObject("idkwtp", Laiks);
            model.addObject("likesGraphics", titles);

            User u = userSession.getLoggedUser();
            List<User> following = usersService.getFollowingUsers(u);
            
            boolean is_following = usersService.getIsFollowing(following, id);

            boolean disable = usersService.getDisable(u, id);
            if (userSession.getLoggedUser().getId() != id) {
                model.addObject("followButton", is_following);
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
        User u1 = userSession.getLoggedUser();

        Optional<User> user1 = usersRepository.findById(u1.getId());
        Optional<User> u2 = usersRepository.findById(id);

        List<User> listfollowing = usersRepository.findFollowing(u1.getUsername());
        List<User> listfollowers = usersRepository.findFollowers(u2.get().getUsername());
        Set<User> setFollowing= new HashSet<>();
        Set<User> setFollower= new HashSet<>();
        //get the Set of following of our user and the Set of followers of the user that we are going to unfollow

        listfollowers.add(user1.get());
        for( User u : listfollowers){
            setFollower.add(u);
        }
        u2.get().setFollowers(setFollower);
        usersRepository.followerNum(u2.get().getId(), setFollower.size());

        listfollowing.add(u2.get()); 
        for( User u : listfollowing){
            setFollowing.add(u);
        }
        u1.setFollowing(setFollowing);
        usersRepository.followingNum(u1.getId(), setFollowing.size());   
        usersRepository.flush();       
        
        response.sendRedirect("../users/"+id);

        
    }

    Console console = System.console();

    @PostMapping("/unfollowAction/{id}")
    public void unfollowAction(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {
        User u1 = userSession.getLoggedUser();
        Optional<User> u2 = usersRepository.findById(id);
        List<User> following=usersRepository.findFollowing(u1.getUsername());
        List<User> follower=usersRepository.findFollowers(u2.get().getUsername());
        Set<User> setFollowers=new HashSet<>();
        Set<User> setFollowing=new HashSet<>();
        //get the List of following of our user and the list of followers of the user that we are going to unfollow
        for(User user:following){
            if(user.getId()==id){
                
            }else{
                setFollowing.add(user);
                
            }
        }
        u1.setFollowing(setFollowing);
        usersRepository.followingNum(u1.getId(), setFollowing.size());
        usersRepository.flush();
        for(User user:follower){
            if(user.getId()==u1.getId()){
                
            }else{
                setFollowers.add(user);
            }
        }
        u2.get().setFollowers(setFollowers);
        usersRepository.followerNum(u2.get().getId(), setFollowers.size());
        usersRepository.flush();
        
        response.sendRedirect("../users/"+id);
        
    }
    
}