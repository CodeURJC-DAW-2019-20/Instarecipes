package com.proyect.instarecipes.controllers;

import java.util.List;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.RankingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RankingWebController{

    @Autowired
    private RankingService rankingService;

    @GetMapping("/ranking")
    public String showRanking(Model model){
        model.addAttribute("rankingList", rankingService.showRanking());
        return "ranking";
    }

    @ModelAttribute
	public void addAttributes(Model model) {
		boolean logged =rankingService.islogged();
        model.addAttribute("logged", logged);
		if(logged){
			model.addAttribute("user",rankingService.getUserLogged());
			model.addAttribute("admin", rankingService.isAdmin());
		}
	}
}