package com.proyect.instarecipes.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class IndexController {
    private List<Recipe> recipes = new ArrayList<>();

	public IndexController() {
        // recipes.add(new Recipe(1, "@boss99", null, "asd", "??? ", "¿¿¿¿", "Homemade Pizza!", "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!", 
        // "ejemplo descripcion2", "15 min.", "Hard"));
        // recipes.add(new Recipe(2, "@lady44", null, "asdasdsasds", "???", "¿¿¿¿", "Avocado Salad", "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese, and an avocado…toss it alltogether, and done. It’s summery, healthy, and so good!", 
        // "ejemplo descripcion2", "15 min.", "Hard"));
    }
    
    @Autowired
    private UsersRepository uRepository;

    @Autowired
    private RecipesRepository recipesRepository;

    // @PostConstruct
    // public void init(){
    //     long id=0;
    //     String username="chiquito";
    //     String email="asdasdas@asddas.com";
    //     String password="12345";
    //     String[] role=null;			//If he's an administrator
    //     String name="pepe";
    //     String surname="surmano";
    //     String allergens="null";
    //     Set<User> followers = new HashSet<User>();
    //     Set<User> following = new HashSet<User>();
    //     uRepository.save(new User(id,
    //                             username,
    //                             email,
    //                             password,
    //                             role,
    //                             name,
    //                             surname,
    //                             allergens,
    //                             followers,
    //                             following)
    //                     );
    // }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("recipes", recipes);
        return "index";
    }
    @GetMapping("/index")
    public String indexedPage(Model model) {
        return "index";
    }
    @GetMapping("/profile") 
    public String profilePage(Model model) {
        return "profile";
    }
    @GetMapping("/ranking")
    public String rankingPage(Model model) {
        return "ranking";
    }
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }
    @GetMapping("/search-page")
    public String searchPage(Model model) {
        return "search-page";
    }








    

    @PostMapping("/")
    public String postRecipe(Model model, Recipe recipe){
        //recipes.add(recipe);
        Recipe r = recipe;
        recipesRepository.save(r);
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
/*
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