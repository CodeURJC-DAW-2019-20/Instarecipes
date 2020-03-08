package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.service.ProfileService;
import com.proyect.instarecipes.service.RequestService;

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
	public interface PostItem extends Request.RequestItems, User.Username, User.NameSurname {
	}

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ProfileService profileservice;
	@Autowired
	private RequestService requestService;

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/")
	public User getUser(@RequestParam Long id) {
		Optional<User> u = usersRepository.findById(id);
		return u.get();
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

	//ADMIN PAGE

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
	
	@JsonView(ProfileRestController.RequestItemView.class)
	@PostMapping("/admin/request")
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

    @JsonView(ProfileRestController.PostItem.class)
    @PostMapping("/sendItemRequest")
    public ResponseEntity<Request> sentItemRequest(@RequestParam("typeOfItem") String typeOfItem,
            @RequestParam("content") String content,
            HttpServletResponse response) {
        boolean status=false;
        User user = requestService.getUser();
        Request request=null;
        boolean exists = false;
        List<Ingredient> ingredientsList = requestService.getIngredients();
        List<Category> categoriesList = requestService.getCategories();
        List<CookingStyle> cookingStylesList = requestService.getCookingStyles();
        // function to get ingredients, categories and cookingstyles (user request)
        if (requestService.isIngredient(typeOfItem)) {
            request = requestService.getNewRequest(user, typeOfItem,content,0);
            exists=requestService.existIngredient(ingredientsList,request);
            status=true;
            //function to verify if the ingredient already exists.
            requestService.saveItem(request,exists);
        }else if (requestService.isCookingStyle(typeOfItem)) {
            request = requestService.getNewRequest(user, typeOfItem, content,1);
            exists=requestService.existCookingStyle(cookingStylesList,request);
            status=true;

            //function to verify if the cookingstyle already exists.
            requestService.saveItem(request,exists);
        }else if (requestService.isCategory(typeOfItem)) {
            request = requestService.getNewRequest(user, typeOfItem, content,2);
            exists=requestService.existCategory(categoriesList,request);
            status=true;
            requestService.saveItem(request,exists);
        }else {
            System.out.println("SELECT A TYPE OF REQUEST ITEM !!");
        }
        if (status) {
			return new ResponseEntity<>(request, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    @JsonView(ProfileRestController.PostItem.class)
    @PostMapping("/actionItemRequest")
    public ResponseEntity<List<Request>>  acceptItemRequest(@RequestParam("typeOfItemRequest") String typeOfRequest, 
    @RequestParam("itemContent") String itemContent,
    @RequestParam("action") String action, 
    @RequestParam("id_request") Long id_request,
    @RequestParam("page") int page_number, @RequestParam("size") int page_size,
    HttpServletResponse response){
        boolean status =false;
        System.out.println("ACTION: "+action);
        System.out.println("Type of request: "+typeOfRequest);
        boolean actionAccepted=requestService.actionIsAccepted(action);
        boolean actionDecline=requestService.actionIsDecline(action);
        if(actionAccepted){
            if(requestService.isEqualIngredient(typeOfRequest)){
                requestService.addItem(0, itemContent, id_request);
                status=true;
                //add ingrediente
            }else if(requestService.isEqualCategory(typeOfRequest)){
                requestService.addItem(1, itemContent, id_request);
                status=true;
                //add categoria
            }else if(requestService.isEqualCookingStyle(typeOfRequest)){
                requestService.addItem(2, itemContent, id_request);
                status=true;
                //add cookingStyle
            }
            //if we accept the item we have to follow a serue of functions to put it right
        }else if(actionDecline){
            requestService.declineItem(id_request);
            status=true;
            //we delete the item through his id
        }
        Page<Request> request = requestService.getRequests(page_number,page_size);
        List<Request> requestList = (List<Request>)request.getContent();
        if (status) {
            //String response_="the item was "+typeOfRequest+" and has been "+action;
			return new ResponseEntity<>(requestList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

}