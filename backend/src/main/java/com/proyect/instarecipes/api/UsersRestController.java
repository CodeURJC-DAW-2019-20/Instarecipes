package com.proyect.instarecipes.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersRestController{
    public interface AnotherUserProfile extends Recipe.RecipeBasic, User.NameSurname,User.Username, User.UserExtraInfo, User.Email, User.Allergen, User.FF,
		Ingredient.Item, CookingStyle.Item, Category.Item{}
    public interface RequestInt extends Ingredient.Item, CookingStyle.Item, Category.Item {}
    
    @Autowired
    private UsersService usersService;
    
    @JsonView(UsersRestController.AnotherUserProfile.class)
    @GetMapping("/")
    public User getUser(@RequestParam Long id) {
        Optional<User> actual = usersService.getActualUser(id);
        // List<Recipe> recipes = profileService.getByUsernameId(id);
        // ArrayList<Integer> Laiks = usersService.getRecipesLikes(recipes);
        // ArrayList<String> titles = usersService.getRecipesTitles(recipes);
        // int likes = usersService.getAllPubsLikes(recipes);
        // List<Category> catList = profileService.getAllCategories();
        // List<Ingredient> ingList = profileService.getAllIngredients();
        // List<CookingStyle> cSList = profileService.getAllCookingStyles();
        // User u = userSession.getLoggedUser();
        // List<User> following = usersService.getFollowingUsers(u);
        // boolean is_following = usersService.getIsFollowing(following, id);
        // boolean disable = usersService.getDisable(u, id);
        return actual.get();
    }
    @JsonView(UsersRestController.RequestInt.class)
    @GetMapping("/requests")
    public List<Request> getAttributes(@RequestParam Long id) {
        return usersService.getRequestList();
    }
}