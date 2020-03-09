package com.proyect.instarecipes.api;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.ProfileService;
import com.proyect.instarecipes.service.RequestService;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import static org.springframework.http.MediaType.IMAGE_JPEG;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {
	public interface UserProfile extends User.NameSurname,User.Username, User.UserExtraInfo, User.Email, User.Allergen, User.FF,
		Ingredient.Item, CookingStyle.Item, Category.Item{}
	public interface AdminProfile extends User.NameSurname,User.Username, User.UserExtraInfo, User.Email, User.Allergen, User.FF,
		Request.RequestItems,Ingredient.Item, CookingStyle.Item, Category.Item{}
	
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ProfileService profileservice;
	@Autowired
	private RequestService requestservice;
	@Autowired
    private UserSession userSession;

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
		Optional <User> u = usersRepository.findById(id);
	   return u.get();
   }
   
    

	@JsonView(ProfileRestController.UserProfile.class)
	@PostMapping("/request")
	public ResponseEntity<Request> requestItem(User user,@RequestParam("ingredient")  String ingredient,@RequestParam("cookingStyle") String cookingStyle,@RequestParam("category") String category){
		if (requestservice.isIngredient(ingredient)) {
		return new ResponseEntity<>(requestservice.getNewRequest(user, "Ingredient", ingredient,0),HttpStatus.OK); 
		}else if(requestservice.isCategory(category)){
		return new ResponseEntity<>(requestservice.getNewRequest(user, "Category", category,1),HttpStatus.OK); 
		}else if(requestservice.isCookingStyle(cookingStyle)){
		return new ResponseEntity<>(requestservice.getNewRequest(user, "Cooking style", cookingStyle,2),HttpStatus.OK); 
		}
		else
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfileImage(@RequestParam("id") Long id) {
        Optional<User> User = usersRepository.findById(id);
        if (!User.isPresent()){
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		else{
        User profile = User.get();
        byte[] image = profile.getImage();
        return new ResponseEntity<>(image, HttpStatus.OK);}
    }

    @PostMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> setProfileImage(@PathVariable Long id, @RequestParam MultipartFile image) throws IOException {
        Optional<User> User = usersRepository.findById(id);
        if (!User.isPresent()){
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		else{
			 User profile = User.get();
			 User u = userSession.getLoggedUser();
        if(u != null && u.getId() == id) {
			profile.setImage(image.getBytes());
			usersRepository.save(profile);
            return new ResponseEntity<>(profile.getImage(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	}
	
	
}


