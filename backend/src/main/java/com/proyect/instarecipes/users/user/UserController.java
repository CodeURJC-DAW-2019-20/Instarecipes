package com.proyect.instarecipes.users.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @GetMapping("/index")
    public String indexLink(Model model){
        return "index";
    }
    @GetMapping("/profile")
    public String profileLink(Model model){
        return "profile";
    }
    @GetMapping("/ranking")
    public String rankingLink(Model model){
        return "ranking";
    }
    @GetMapping("/search-page")
    public String searchPageLink(Model model){
        return "search-page";
    }
    @GetMapping("/login")
    public String loginLink(Model model){
        return "login";
    }
    @GetMapping("/simple-recipe")
    public String simpleRecipeLink(Model model){
        return "simple-recipe";
    }
}