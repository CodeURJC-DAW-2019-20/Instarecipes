package com.proyect.instarecipes.views;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

@Controller
public class IndexController {

    private List<Recipe> recipes = new ArrayList<>();

    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/")
    public String indexPage(Model model) {
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }
    // @GetMapping("/index")
    // public String indexedPage() {
    //     return "index";
    // }
    @GetMapping("/profile") 
    public String profilePage() {
        return "profile";
    }
    @GetMapping("/ranking")
    public String rankingPage() {
        return "ranking";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/search-page")
    public String searchPage() {
        return "search-page";
    }
    @GetMapping("/loginerror")
    public String loginerror() {
    	return "loginerror";
    }
    @GetMapping("/admin-profile")
    public String adminProfile() {
    	return "admin-profile";
    }

    //Attempt to enter in a link that he cant if not logged in
    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
    	User user = usersRepository.findByName(request.getUserPrincipal().getName());
    	model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
    	model.addAttribute("username", user.getName());
    	
    	return "index ";
    }

    // @RequestMapping("/error")
    // public String errorPage(Model model){
    //     return "404";
    // }
    // @ModelAttribute
	// public void addUserToModel(Model model) {
	// 	boolean logged = userSession.getLoggedUser() != null;
	// 	model.addAttribute("logged", logged);
	// 	if(logged) {
	// 		model.addAttribute("role", userSession.getLoggedUser().toString());
	// 		model.addAttribute("username",userSession.getLoggedUser().getName());
	// 		if(userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN")){
	// 			model.addAttribute("admin",userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
	// 			model.addAttribute("user",userSession.getLoggedUser().getRoles().contains("ROLE_USER"));
	// 		}
	// 	}
    // }

    // @GetMapping("/error")
	// public ModelAndView showError(Model model) {
	// 	ModelAndView newModel = new ModelAndView("404");
	// 	return newModel;
	// }




    

    @PostMapping("/")
    public String postRecipe(Model model, Recipe recipe) {
        //recipes.add(recipe);
        Recipe r = recipe;
        recipesRepository.save(r);
        List<Recipe> recipes = recipesRepository.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    User u = new User();

    @PostMapping("/signUp")
    public String loginPage(Model model, User user){
        u = user;
        return "signUp";
    }
/* DO NOT DELETE THIS CODE FOR THE MOMENT
    @PostMapping("/index")
    public String registerUser(Model model, User user){
        u.setName(user.getName());
        if(user.getSurname()!=null){
            u.setSurname(user.getSurname());
        }else{
            u.setSurname("null");
        }
        if(user.getAllergens()!="null"){
            u.setAllergens(user.getAllergens());
        }else{
            u.setAllergens("null");
        }
        u.setRole(null);
        uRepository.save(u);

        model.addAttribute("recipes", this.getRecipes());

        return "index";
    }
    */
}