package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.repositories.UsersRepository;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {
	public interface UserProfile extends User.NameSurname,User.Username, User.UserExtraInfo, User.Email, User.Allergen, User.FF,
		Ingredient.Item, CookingStyle.Item, Category.Item{}
	public interface AdminProfile extends User.NameSurname,User.Username, User.UserExtraInfo, User.Email, User.Allergen, User.FF,
		Request.RequestItems,Ingredient.Item, CookingStyle.Item, Category.Item{}
	
	@Autowired
	private UsersRepository usersRepository;

	@JsonView(ProfileRestController.UserProfile.class)							
	@GetMapping("/")
 	public User getUser(@RequestParam Long id) {
		 Optional <User> u = usersRepository.findById(id);
		return u.get();
	} 

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/{id}")
	public User getUser(@PathVariable long id) {
		return usersRepository.findById(id).get();
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/username")
	public Collection<String> getUsernames() {
		return usersRepository.findAll().stream().map(b -> b.getUsername()).collect(Collectors.toList());
	}
	
	@JsonView(ProfileRestController.UserProfile.class)							
	@GetMapping("/admin")
	public User getAdmin(@RequestParam Long id) {
		Optional <User> u = usersRepository.findById(id);
	   return u.get();
   }

}