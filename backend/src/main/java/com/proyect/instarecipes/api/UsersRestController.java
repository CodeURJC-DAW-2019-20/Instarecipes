package com.proyect.instarecipes.api;

import java.io.IOException;
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
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.service.ProfileService;
import com.proyect.instarecipes.service.RequestService;
import com.proyect.instarecipes.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {
    public interface AnotherUserProfile extends Recipe.RecipeBasic, User.NameSurname, User.Username, User.UserExtraInfo,
            User.Email, User.Allergen, User.FF, Ingredient.Item, CookingStyle.Item, Category.Item {}
    public interface RequestInt extends Ingredient.Item, CookingStyle.Item, Category.Item {}

    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProfileService profileservice;
    @Autowired
    private RequestService requestservice;

    @JsonView(UsersRestController.AnotherUserProfile.class)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws IOException {
        User u = usersRepository.findById(id).get();
		if (id != null) {
			return new ResponseEntity<>(profileservice.updateUser(u,null,null, profileservice.getName(id), profileservice.getSurName(id),
					profileservice.getAllergen(id), profileservice.getInfo(id)), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    @JsonView(UsersRestController.RequestInt.class)
    @GetMapping("/requests")
    public List<Request> getAttributes(@RequestParam Long id) {
        return usersService.getRequestList();
    }

}