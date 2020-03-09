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
import com.proyect.instarecipes.security.UserSession;
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
	@Autowired
	private UserSession userSession;

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/")
	public User getUser(@RequestParam Long id) {
		Optional<User> u = usersRepository.findById(id);
		return u.get();
	}

	@JsonView(ProfileRestController.UserProfile.class)
	@PutMapping("/update")
	public ResponseEntity<User> updateProfile(@RequestParam(required = false) String name, @RequestParam(required = false) String surname, 
	@RequestParam(required = false) String allergen, @RequestParam(required = false) String info, @RequestParam(required = false)  MultipartFile avatar,
	 @RequestParam(required = false) MultipartFile background )
			throws IOException {
		if(userSession.isLoggedUser()){
			User u = usersRepository.findById(userSession.getLoggedUser().getId()).get();
			if (userSession.getLoggedUser().getId() != null) {
				return new ResponseEntity<>(profileservice.updateUser(u,avatar,background, name, surname,
						allergen,info), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	//ADMIN PAGE

	@JsonView(ProfileRestController.UserProfile.class)
	@GetMapping("/admin")
	public ResponseEntity<User> getAdmin() {
		if(userSession.isLoggedUser()){
			boolean isAdmin = false;
			for(String s : userSession.getLoggedUser().getRoles()){
				if(s.equals("ROLE_ADMIN"))
					isAdmin = true;				
			}
			if(isAdmin){
				User u = userSession.getLoggedUser();
				return new ResponseEntity<>(u, HttpStatus.OK);
			}else{
				return new ResponseEntity<>(HttpStatus.LOCKED);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}

	@JsonView(ProfileRestController.AdminProfile.class)
	@GetMapping("/admin/users")
	public ResponseEntity<List<User>> getUsersList() {
		if(userSession.isLoggedUser()){
			boolean isAdmin = false;
			for(String s : userSession.getLoggedUser().getRoles()){
				if(s.equals("ROLE_ADMIN"))
					isAdmin = true;				
			}
			if(isAdmin){
				List <User> u = usersRepository.findAll();
				return new ResponseEntity<>(u,HttpStatus.OK);
			}else{
				return new ResponseEntity<>(HttpStatus.LOCKED);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
	}
	
	@JsonView(ProfileRestController.RequestItemView.class)
	@PostMapping("/admin/request")
	public ResponseEntity<Request> requestItem(@RequestParam("typeOfItem") String typeOfItem, @RequestParam("content") String content,
			HttpServletResponse response) {
		if(userSession.isLoggedUser()){
			boolean isAdmin = false;
			for(String s : userSession.getLoggedUser().getRoles()){
				if(s.equals("ROLE_ADMIN"))
					isAdmin = true;				
			}
			if(isAdmin){
				Optional<User> user = usersRepository.findById(userSession.getLoggedUser().getId());
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
			} else{
				return new ResponseEntity<>(HttpStatus.LOCKED);
			}
		} else{
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}
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
    public ResponseEntity<List<Request>> acceptItemRequest (@RequestParam("typeOfItemRequest") String typeOfRequest, 
    @RequestParam("itemContent") String itemContent,
    @RequestParam("action") String action, 
    @RequestParam("id_request") Long id_request,
    @RequestParam("page") int page_number, @RequestParam("size") int page_size){
        boolean status =false;
        boolean actionAccepted=requestService.actionIsAccepted(action);
        boolean actionDecline=requestService.actionIsDecline(action);
        if(actionAccepted){
            if(requestService.isEqualIngredient(typeOfRequest)){
                requestService.addItem(0, itemContent, id_request);
                status=true;
                //add ingredient
            }else if(requestService.isEqualCategory(typeOfRequest)){
                requestService.addItem(1, itemContent, id_request);
                status=true;
                //add category
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