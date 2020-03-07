package com.proyect.instarecipes.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RequestsRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ProfileWebController {

    @Autowired
    private UserSession userSession;
    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        User actual = userSession.getLoggedUser();
        // User
        model.addAttribute("actualUser", actual);
        // All users
        model.addAttribute("usersList", profileService.getAllUser());
        // Number of followers
        model.addAttribute("n_followers", profileService.getFollowersCount(actual.getUsername()));
        // Number of following
        model.addAttribute("n_following", profileService.getFollowingCount(actual.getUsername()));
        // Followers
        model.addAttribute("followers", profileService.getAllFollowers(actual.getUsername()));
        // Following
        model.addAttribute("following", profileService.getAllFollowing(actual.getUsername()));
        // Number of publications and total likes
        List<Recipe> recipes = profileService.getByUsernameId(actual.getId());
        List<Integer> details = profileService.getUserRecipeDetails(actual.getId());       
        model.addAttribute("n_publications", details.get(0));
        model.addAttribute("n_likes", details.get(1));
        // Publications
        model.addAttribute("publications", recipes);
        model.addAttribute("idkwtp", profileService.getLaiks(recipes));
        model.addAttribute("likesGraphics", profileService.getTitles(recipes));
        model.addAttribute("allergensList", profileService.getAllAllergens());
        model.addAttribute("ingredientsList", profileService.getAllIngredients());
        model.addAttribute("cookingStylesList", profileService.getAllCookingStyles());
        model.addAttribute("categoriesList", profileService.getAllCategories());
        return "profile";
    }

    @PostMapping("/settings")
    public void settings(@RequestParam String name,@RequestParam String surname,@RequestParam String info, @RequestParam String allergens, HttpServletResponse response, 
            @RequestParam MultipartFile avatarFile, @RequestParam MultipartFile backgroundFile) throws IOException {

        User u = userSession.getLoggedUser();
        profileService.updateUser(u, avatarFile, backgroundFile, name, surname, allergens, info);
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