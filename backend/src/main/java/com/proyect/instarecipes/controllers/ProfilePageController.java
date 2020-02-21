package com.proyect.instarecipes.controllers;

import java.util.List;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.RequestsRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProfilePageController{

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private RequestsRepository requestsRepository;

    @GetMapping("/profile") 
    public String profilePage(Model model) {
        User actual =  userSession.getLoggedUser();
        //Name
        model.addAttribute("name", actual.getName());
        //Surname
        model.addAttribute("surname", actual.getSurname());
        //Username
        model.addAttribute("username", actual.getUsername());
        //Info
        model.addAttribute("info",actual.getInfo());
        //Number of followers
        model.addAttribute("n_followers", usersRepository.findFollowers(actual.getUsername()).size());
        //Number of following
        model.addAttribute("n_following", usersRepository.findFollowing(actual.getUsername()).size());
        //Followers
        model.addAttribute("followers", usersRepository.findFollowers(actual.getUsername()));
        //Following
        model.addAttribute("following", usersRepository.findFollowing(actual.getUsername()));
        //Number of publications and total likes
        List<Recipe> recipes = recipesRepository.findByUsernameId(actual.getId());
        int likes = 0;
        int pubs;
        for(pubs=0; pubs<recipes.size();pubs++){
            likes = likes + recipes.get(pubs).getLikes();
        }
        model.addAttribute("n_publications", pubs);
        model.addAttribute("n_likes", likes);
        //Publications
        model.addAttribute("publications", recipes);

        return "profile";
    }

    @ModelAttribute
	public void addAttributes(Model model) {
		boolean logged = userSession.getLoggedUser() != null;
        model.addAttribute("logged", logged);
		if(logged){
			model.addAttribute("user",userSession.getLoggedUser().getUsername());
			model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
        }
        System.out.println("LISTA DE REQueST: " + requestsRepository.findAll());
        //if(userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN")){
            List<Request> requestsList = requestsRepository.findAll();

            model.addAttribute("allRequests", requestsList);
        //}
    }
}