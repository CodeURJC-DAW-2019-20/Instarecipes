package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.ProfileService;
import com.proyect.instarecipes.service.RequestService;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {
	public interface UserProfile extends User.NameSurname, User.Username, User.UserExtraInfo, User.Email, User.Allergen,
	User.FF, Ingredient.Item, CookingStyle.Item, Category.Item {}
	public interface SimpleRecipe extends Recipe.RecipeView, Recipe.IDRecipe,
	Recipe.RecipeBasic, Recipe.RecipePlus, Recipe.RecipeExtra, Ingredient.Item, Category.Item, User.Username, User.NameSurname, Recipe.Rankinglikes{}
	public interface RequestItemView extends User.NameSurname, User.Username, Request.RequestItems {}
	public interface AdminProfile extends User.NameSurname, User.Username, User.UserExtraInfo, User.Email,
	User.Allergen, User.FF, Request.RequestItems, Ingredient.Item, CookingStyle.Item, Category.Item {}
	public interface PostItem extends Request.RequestItems, User.Username, User.NameSurname {}

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ProfileService profileservice;
	@Autowired
	private RequestService requestService;
	@Autowired
	private UserSession userSession;

	// SHOW OWN PROFILE
	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/")
	public ResponseEntity<User> getUser() {
		if (userSession.isLoggedUser()) {
			User u = usersRepository.findByUsername(userSession.getLoggedUser().getUsername());
			return new ResponseEntity<>(u, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	//SHOW OWN AVATAR
	@GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getProfileImage() throws IOException {
		if(userSession.isLoggedUser()){
            User user = usersRepository.findByUsername(userSession.getLoggedUser().getUsername());
			if(user.getImage().length > 0){
				byte[] image = user.getImage();
				return new ResponseEntity<>(image, HttpStatus.OK);
			}else{
				File file = new File("temp/avatars/image-"+user.getId()+".jpg");
				return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	//SHOW OWN BACKGROUND
	@GetMapping(value = "/background", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getBackgroundImage() throws IOException {
		if(userSession.isLoggedUser()){
			User user = userSession.getLoggedUser();
			if(user.getImageBackground().length > 0){
				byte[] image = user.getImageBackground();
				return new ResponseEntity<>(image, HttpStatus.OK);
			}else{
				File file = new File("temp/backgrounds/image-"+user.getId()+".jpg");
				return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// UPDATE OWN USER
	@JsonView(ProfileRestController.UserProfile.class)
	@PutMapping("/update")
	public ResponseEntity<User> updateProfile(@RequestBody User user) throws IOException {
		if(userSession.isLoggedUser()){
			return new ResponseEntity<>(profileservice.updateUser(user), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// UPDATE OWN USER PROFILE AVATAR
	@PutMapping(value = "/update/avatar", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> updateProfileAvatar(@RequestParam MultipartFile avatar) throws IOException {
		if(userSession.isLoggedUser()){
			return new ResponseEntity<>(profileservice.updateUserAvatar(avatar), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// UPDATE OWN USER PROFILE BACKGROUND
	@PutMapping(value = "/update/background", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> updateProfileBackground(@RequestParam MultipartFile background) throws IOException {
		if(userSession.isLoggedUser()){
			return new ResponseEntity<>(profileservice.updateUserBackground(background), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// SEND AN ITEM REQUEST
	@JsonView(ProfileRestController.PostItem.class)
	@PostMapping("/sendItemRequest")
	public ResponseEntity<Request> sentItemRequest(@RequestBody Request request) {
		boolean status = false;
		User user = usersRepository.findByUsername(requestService.getUser().getUsername());
		boolean exists = false;
		Request r = null;
		if (userSession.isLoggedUser()) {
			List<Ingredient> ingredientsList = requestService.getIngredients();
			List<Category> categoriesList = requestService.getCategories();
			List<CookingStyle> cookingStylesList = requestService.getCookingStyles();
			if (requestService.isEqualIngredient(request.getTypeOfRequest())) {
				r = requestService.getNewRequest(user, request.getTypeOfRequest(), request.getIngredientContent(), 0);
				exists = requestService.existIngredient(ingredientsList, request.getIngredientContent());
				status = true;
				requestService.saveItem(r, exists);
			} else if (requestService.isEqualCookingStyle(request.getTypeOfRequest())) {
				r = requestService.getNewRequest(user, request.getTypeOfRequest(), request.getCookingStyleContent(), 1);
				exists = requestService.existCookingStyle(cookingStylesList, request.getCookingStyleContent());
				status = true;
				requestService.saveItem(r, exists);
			} else if (requestService.isEqualCategory(request.getTypeOfRequest())) {
				r = requestService.getNewRequest(user, request.getTypeOfRequest(), request.getCategoryContent(), 2);
				exists = requestService.existCategory(categoriesList, request.getCategoryContent());
				status = true;
				requestService.saveItem(r, exists);
			}
			if (status) {
				return new ResponseEntity<>(r, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
    }

	// ADMIN PAGE

	// ADMIN'S PROFILE
	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/admin")
	public ResponseEntity<User> getAdmin() {
		if (userSession.isLoggedUser()) {
			if (userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN")) {
				User u = userSession.getLoggedUser();
				return new ResponseEntity<>(u, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.LOCKED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// LIST OF USERS
	@JsonView(ProfileRestController.AdminProfile.class)
	@GetMapping("/admin/users")
	public ResponseEntity<List<User>> getUsersList() {
		if (userSession.isLoggedUser()) {
			boolean isAdmin = false;
			for (String s : userSession.getLoggedUser().getRoles()) {
				if (s.equals("ROLE_ADMIN"))
					isAdmin = true;
			}
			if (isAdmin) {
				List<User> u = usersRepository.findAll();
				return new ResponseEntity<>(u, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.LOCKED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// SHOW ANOTHER USER'S PROFILE IMAGE ?FROM THE LIST OF USERS?
	@GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> setProfileImage(@PathVariable Long id) throws IOException {
		if(userSession.isLoggedUser()){
			if(userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN")){
				User user = usersRepository.findById(id).get();
				if(user.getImage().length > 0){
					byte[] image = user.getImage();
					return new ResponseEntity<>(image, HttpStatus.OK);
				}else{
					File file = new File("temp/avatars/image-"+user.getId()+".jpg");
					return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
				}
			}else{
				return new ResponseEntity<>(HttpStatus.LOCKED);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// LIST OF REQUESTS
	@JsonView(ProfileRestController.RequestItemView.class)
	@GetMapping("/admin/requests")
	public ResponseEntity<List<Request>> requestItem(){
		if(userSession.isLoggedUser()){
			if(userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN")){
				return new ResponseEntity<>(requestService.getRequests(), HttpStatus.OK);
			}else{
				return new ResponseEntity<>(HttpStatus.LOCKED);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	// ACCEPT OR DECLINE ITEM REQUEST
    @JsonView(ProfileRestController.PostItem.class)
    @GetMapping("/actionItemRequest")
    public ResponseEntity<List<Request>> acceptItemRequest (@RequestParam String typeOfRequest, 
    @RequestParam String itemContent,@RequestParam String action, @RequestParam Long id_request){
		if(userSession.isLoggedUser()){
			if(userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN")){
				boolean status =false;
				boolean actionAccepted=requestService.actionIsAccepted(action);
				boolean actionDecline=requestService.actionIsDecline(action);
				if(actionAccepted){
					if(requestService.isEqualIngredient(typeOfRequest)){
						requestService.addItem(0, itemContent, id_request);
						status=true;
					}else if(requestService.isEqualCategory(typeOfRequest)){
						requestService.addItem(1, itemContent, id_request);
						status=true;
					}else if(requestService.isEqualCookingStyle(typeOfRequest)){
						requestService.addItem(2, itemContent, id_request);
						status=true;
					}
				}else if(actionDecline){
					requestService.declineItem(id_request);
					status=true;
				}
				if(status){
					return new ResponseEntity<>(requestService.getRequests(), HttpStatus.OK);
				}else{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}else{
				return new ResponseEntity<>(HttpStatus.LOCKED);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}
	
	@JsonView(ProfileRestController.SimpleRecipe.class)
	@GetMapping("/{id}/recipes")
	public ResponseEntity<List<Recipe>> getAllRecipes( @PathVariable Long id) {
		return new ResponseEntity<>(profileservice.getByUsernameId(id), HttpStatus.OK);
	}

	@GetMapping("/allAllergens")
	public ResponseEntity<List<Allergen>> getAllAllergens() {
		return new ResponseEntity<>(profileservice.getAllAllergens(), HttpStatus.OK);
	}

	@GetMapping("/allCookingStyles")
	public ResponseEntity<List<CookingStyle>> getAllCookingStyles(){
		return new ResponseEntity<>(profileservice.getAllCookingStyles(), HttpStatus.OK);
	}

	@GetMapping("/allCategories")
	public ResponseEntity<List<Category>> getAllCategories(){
		return new ResponseEntity<>(profileservice.getAllCategories(), HttpStatus.OK);
	}

	@GetMapping("/allIngredients")
	public ResponseEntity<List<Ingredient>> getAllIngredients(){
		return new ResponseEntity<>(profileservice.getAllIngredients(), HttpStatus.OK);
	}
}