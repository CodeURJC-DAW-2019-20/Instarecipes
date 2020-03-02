package com.proyect.instarecipes.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.RequestsRepository;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ProfilePageController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private AllergensRepository allergensRepository;
    @Autowired
    private ImageService imageService;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        User actual = userSession.getLoggedUser();
        // User
        model.addAttribute("actualUser", actual);
        // All users
        model.addAttribute("usersList", usersRepository.findAll());
        // Number of followers
        model.addAttribute("n_followers", usersRepository.findFollowers(actual.getUsername()).size());
        // Number of following
        model.addAttribute("n_following", usersRepository.findFollowing(actual.getUsername()).size());
        // Followers
        model.addAttribute("followers", usersRepository.findFollowers(actual.getUsername()));
        // Following
        model.addAttribute("following", usersRepository.findFollowing(actual.getUsername()));
        // Number of publications and total likes
        List<Recipe> recipes = recipesRepository.findByUsernameId(actual.getId());
        ArrayList<Integer> Laiks = new ArrayList<Integer>();
        ArrayList<String> titles = new ArrayList<String>();
        // ArrayList<String> finallyTitles = new
        // ArrayList<String>(Arrays.asList(recipes.toString().split(",")));
        int likes = 0;
        int pubs;

        for (pubs = 0; pubs < recipes.size(); pubs++) {
            likes = likes + recipes.get(pubs).getLikes();
            Laiks.add(recipes.get(pubs).getLikes()); // List of every user recipe LIKES!!
            titles.add(recipes.get(pubs).getTitle());
        }


        model.addAttribute("n_publications", pubs);
        model.addAttribute("n_likes", likes);
        // Publications
        model.addAttribute("publications", recipes);
        model.addAttribute("idkwtp", Laiks);
        model.addAttribute("likesGraphics", titles);
        List<Allergen> allergensList = allergensRepository.findAll();
        model.addAttribute("allergensList", allergensList);

        return "profile";
    }


    @PostMapping("/settings")
    public void settings(@RequestParam String name,@RequestParam String surname,@RequestParam String info, @RequestParam String allergens, HttpServletResponse response, 
            @RequestParam MultipartFile avatarFile, @RequestParam MultipartFile backgroundFile) throws IOException {

        User u = userSession.getLoggedUser();
        u.setName(name);
        u.setSurname(surname);
        u.setAllergens(allergens);
        u.setInfo(info);
        usersRepository.flush();
        if(!avatarFile.isEmpty()){
            imageService.saveImage("avatars", u.getId(), avatarFile);
        }
        if(!backgroundFile.isEmpty()){
            imageService.saveImage("backgrounds", u.getId(), backgroundFile);
        }
        
        response.sendRedirect("profile");

      
    }

    @ModelAttribute
	public void addAttributes(Model model) {
		boolean logged = userSession.getLoggedUser() != null;
        model.addAttribute("logged", logged);
		if(logged){
			model.addAttribute("user",userSession.getLoggedUser().getUsername());
			model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
        }
        List<Request> requestsList = requestsRepository.findAll();
        model.addAttribute("allRequests", requestsList);
    }
}