package com.proyect.instarecipes.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.service.ProfileService;
import com.proyect.instarecipes.service.RequestsService;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {
	public interface UserProfile extends User.NameSurname, User.Username, User.UserExtraInfo, User.Email, User.Allergen,
			User.FF, Ingredient.Item, CookingStyle.Item, Category.Item, Request.RequestItems {
	}

	public interface AdminProfile extends User.NameSurname, User.Username, User.UserExtraInfo, User.Email,
			User.Allergen, User.FF, Request.RequestItems, Ingredient.Item, CookingStyle.Item, Category.Item {
	}

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ProfileService profileservice;
	@Autowired
	private RequestsService requestsService;

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/")
	public User getUser(@RequestParam Long id) {
		Optional<User> u = usersRepository.findById(id);
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
	@GetMapping("/name")
	public Collection<String> getNames() {
		return usersRepository.findAll().stream().map(b -> b.getName()).collect(Collectors.toList());
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/email")
	public Collection<String> getEmail() {
		return usersRepository.findAll().stream().map(b -> b.getEmail()).collect(Collectors.toList());
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/allergens")
	public Collection<String> getAllergens() {
		return usersRepository.findAll().stream().map(b -> b.getAllergens()).collect(Collectors.toList());
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/info")
	public Collection<String> getInfo() {
		return usersRepository.findAll().stream().map(b -> b.getInfo()).collect(Collectors.toList());
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/surname")
	public Collection<String> getSurname() {
		return usersRepository.findAll().stream().map(b -> b.getSurname()).collect(Collectors.toList());
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/nfollowing")
	public Collection<String> getFollowingCount() {
		return usersRepository.findAll().stream().map(b -> b.getSurname()).collect(Collectors.toList());
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/admin")
	public User getAdmin(@RequestParam Long id) {
		Optional<User> u = usersRepository.findById(id);
		return u.get();
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@PutMapping("/")
	public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody User profileupdated)
			throws IOException {
		User u = usersRepository.findById(id).get();
		if (id != null) {
			return new ResponseEntity<>(profileservice.updateUser(u, profileservice.getName(id),
					profileservice.getSurName(id), profileservice.getAllergen(id), profileservice.getInfo(id)),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@PostMapping("/request")
	public ResponseEntity<Request> requestItem(@RequestParam("typeOfItem") String typeOfItem,
			@RequestParam("content") String content, HttpServletResponse response) {
		User user = requestsService.getUser();
		Request req = null;
		if (requestsService.isIngredient(typeOfItem)) {
			req = requestsService.getNewRequest(user, typeOfItem, content, 0);
			return new ResponseEntity<>(req, HttpStatus.OK);
		} else if (requestsService.isCategory(typeOfItem)) {
			req = requestsService.getNewRequest(user, typeOfItem, content, 1);
			return new ResponseEntity<>(req, HttpStatus.OK);
		} else if (requestsService.isCookingStyle(typeOfItem)) {
			req = requestsService.getNewRequest(user, typeOfItem, content, 2);
			return new ResponseEntity<>(req, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}