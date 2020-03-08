package com.proyect.instarecipes.controllers;

import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.RankingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RankingWebController{

    @Autowired
    private RankingService rankingService;
    @Autowired
    private UserSession userSession;

    @GetMapping("/ranking")
    public String showRanking(Model model){
        model.addAttribute("rankingList", rankingService.showRanking());
        return "ranking";
    }

    @ModelAttribute
	public void addAttributes(Model model) {
		boolean logged = userSession.isLoggedUser();
        model.addAttribute("logged", logged);
		if(logged){
			model.addAttribute("user", userSession.getLoggedUser().getUsername());
			model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		}
	}

}