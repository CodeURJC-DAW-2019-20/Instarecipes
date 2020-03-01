package com.proyect.instarecipes.controllers;

import java.util.List;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.security.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RankingPageController{
    @Autowired
    private UserSession userSession;
    @Autowired
    private RecipesRepository recipesRepository;

    @GetMapping("/ranking")
    public String showRanking(Model model){
        Page<Recipe> rankingPage = recipesRepository.findTopTen(PageRequest.of(0, 10));
        List<Recipe> ranking = rankingPage.getContent();
        model.addAttribute("rankingList", ranking);
        return "ranking";
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