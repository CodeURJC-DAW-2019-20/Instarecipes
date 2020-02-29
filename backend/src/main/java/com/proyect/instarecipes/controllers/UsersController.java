package com.proyect.instarecipes.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

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
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UserSession userSession;

    @GetMapping("/users/{id}")
    public ModelAndView anotherUserProfile(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("profile");
        Optional<User> actual = usersRepository.findById(id);
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
        List<Recipe> recipes = recipesRepository.findByUsernameId(id);
        ArrayList<Integer> Laiks = new ArrayList<Integer>();
        ArrayList<String> titles = new ArrayList<String>();

        int likes = 0;
        int pubs;

        for (pubs = 0; pubs < recipes.size(); pubs++) {
            likes = likes + recipes.get(pubs).getLikes();
            Laiks.add(recipes.get(pubs).getLikes()); // List of every user recipe LIKES!!
            titles.add(recipes.get(pubs).getTitle());
        }
        System.out.println(Laiks);
        System.out.println(titles);

        model.addObject("n_publications", pubs);
        model.addObject("n_likes", likes);
        // Publications
        model.addObject("publications", recipes);
        model.addObject("idkwtp", Laiks);
        model.addObject("likesGraphics", titles);

        User u = userSession.getLoggedUser();
        System.out.println("User: " + u);
        List<User> following = usersRepository.findFollowing(u.getUsername());
        System.out.println("Following: " + following);
        boolean is_following = false;
        for (User user : following) {
            System.out.println("User: " + user.getUsername());
            if (user.getId() != id) {
                is_following = false;
            } else {
                is_following = true;
                break;
            }
        }
        boolean disable = false;
        if (userSession.getLoggedUser().getId() != id) {
            model.addObject("followButton", is_following);
        }
        if (u.getId() == id) {
            disable = false;
            model.addObject("disable", disable);
        } else {
            disable = true;
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
    }

    @PostMapping("/followAction/{id}")
    public String followAction(@PathVariable Long id, Model model) {

        // User u1 = userSession.getLoggedUser();
        // // Optional<User> u2 = usersRepository.findById(id);
        // // u1.addFollowing(u2.get());
        // GroupStaff groupStaff = new GroupStaff();
        // User u2 = new User("MARIANO", "MARIANO@MARIANO.MARIANO", "pass", "MARIANO",
        // "MARIANO", "Hello World !!", "sida", null, null, "ROLE_USER");
        // usersRepository.save(u2);
        // User u3 = new User("user2", "manu@gmail.com", "pass", "Manuel", "Savater",
        // "Konichiwa people !", "awp's", null, null, "ROLE_USER");
        // User u4 = new User("user3", "trevodrap@hotmail.com", "pass", "Trevod",
        // "Rap","Hello people !", "Toyacos", null, null, "ROLE_USER");
        // Set<User> foll = groupStaff.groupFollowing(u2);

        // usersRepository.following(u1.getId(), foll);

        return "profile";
    }

    Console console = System.console();

    @PostMapping("/unfollowAction/{id}")
    public void unfollowAction(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {

        User u1 = userSession.getLoggedUser();
        System.out.println("Usuario 1: "+u1);
        Optional<User> u2 = usersRepository.findById(id);
        System.out.println("Usuario 1: "+u2.get());
        List<User> followers_list = usersRepository.findFollowers(u1.getUsername());
        Set<User> followers=new HashSet<User>();
        for(User user:followers_list){
            followers.add(user);
        }
        System.out.println("Usuario 1 followers: "+followers);
        followers.add(u2.get());
        //usersRepository.followers_update(u1.getId(), followers);
        //followers = usersRepository.findFollowers(u1.getUsername());
        System.out.println("Usuario 1 followers actualizado: "+u1.getFollowers());
        //u2.get().addFollowing(u1);
        
        
        response.sendRedirect("/users/"+id);
    }
    
}