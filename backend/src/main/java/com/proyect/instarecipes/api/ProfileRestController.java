package com.proyect.instarecipes.api;

import org.apache.tomcat.util.http.parser.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
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
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.ProfileService;
import com.proyect.instarecipes.service.RequestService;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {
	public interface UserProfile extends User.NameSurname, User.Username, User.UserExtraInfo, User.Email, User.Allergen,
			User.FF, Ingredient.Item, CookingStyle.Item, Category.Item {
	}

	public interface RequestItemView extends User.NameSurname, User.Username, Request.RequestItems {
	}

	public interface AdminProfile extends User.NameSurname, User.Username, User.UserExtraInfo, User.Email,
			User.Allergen, User.FF, Request.RequestItems, Ingredient.Item, CookingStyle.Item, Category.Item {
	}

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ProfileService profileservice;
	@Autowired
	private RequestService requestService;
	@Autowired
	private UserSession userSession;

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/")
	public User getUser(@RequestParam Long id) {
		Optional<User> u = usersRepository.findById(id);
		return u.get();
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

	@JsonView(ProfileRestController.AdminProfile.class)
	@GetMapping("/admin/users")
	public List <User> getUsersList() {
		List <User> u = usersRepository.findAll();
		return u;
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@PutMapping("/")
	public ResponseEntity<User> updateProfile(@RequestParam Long id, @RequestParam String name, @RequestParam String surname, @RequestParam String allergen, @RequestParam String info,
	 @RequestParam  MultipartFile avatar, @RequestParam MultipartFile background )
			throws IOException {
		User u = usersRepository.findById(id).get();
		if (id != null) {
			return new ResponseEntity<>(profileservice.updateUser(u,avatar,background, name, surname,
					allergen,info), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@JsonView(ProfileRestController.RequestItemView.class)
	@PostMapping("/request")
	public ResponseEntity<Request> requestItem(@RequestParam Long id_user,
			@RequestParam("typeOfItem") String typeOfItem, @RequestParam("content") String content,
			HttpServletResponse response) {
		Optional<User> user = usersRepository.findById(id_user);
		Request req = null;
		if (requestService.isIngredient(typeOfItem)) {
			req = requestService.getNewRequest(user.get(), typeOfItem, content, 0);
			return new ResponseEntity<>(req, HttpStatus.OK);
		} else if (requestService.isCategory(typeOfItem)) {
			req = requestService.getNewRequest(user.get(), typeOfItem, content, 1);
			return new ResponseEntity<>(req, HttpStatus.OK);
		} else if (requestService.isCookingStyle(typeOfItem)) {
			req = requestService.getNewRequest(user.get(), typeOfItem, content, 2);
			return new ResponseEntity<>(req, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}/image")
	public ResponseEntity<byte[]> getProfileImage(@RequestParam("id") Long id) {
		Optional<User> User = usersRepository.findById(id);
		if (!User.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			User profile = User.get();
			byte[] image = profile.getImage();
			return new ResponseEntity<>(image, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/{id}/image")
	public ResponseEntity<byte[]> setProfileImage(@PathVariable Long id, @RequestParam MultipartFile image)
			throws IOException {
		Optional<User> User = usersRepository.findById(id);
		if (!User.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			User profile = User.get();
			User u = userSession.getLoggedUser();
			if (u != null && u.getId() == id) {
				profile.setImage(image.getBytes());
				usersRepository.save(profile);
				return new ResponseEntity<>(profile.getImage(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
	}

}