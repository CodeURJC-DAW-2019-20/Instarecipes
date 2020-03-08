package com.proyect.instarecipes.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class RequestRestController {

    public interface PostItem extends Request.RequestItems, User.Username, User.NameSurname {}

    @Autowired
    private RequestService requestsService;

    @JsonView(RequestRestController.PostItem.class)
    @PostMapping("/sendItemRequest")
    public ResponseEntity<Request> sentItemRequest(@RequestParam("typeOfItem") String typeOfItem,
            @RequestParam("content") String content,
            HttpServletResponse response) {
        boolean status=false;
        User user = requestsService.getUser();
        Request request=null;
        boolean exists = false;
        List<Ingredient> ingredientsList = requestsService.getIngredients();
        List<Category> categoriesList = requestsService.getCategories();
        List<CookingStyle> cookingStylesList = requestsService.getCookingStyles();
        // function to get ingredients, categories and cookingstyles (user request)
        if (requestsService.isIngredient(typeOfItem)) {
            request = requestsService.getNewRequest(user, typeOfItem,content,0);
            exists=requestsService.existIngredient(ingredientsList,request);
            status=true;
            //function to verify if the ingredient already exists.
            requestsService.saveItem(request,exists);
        }else if (requestsService.isCookingStyle(typeOfItem)) {
            request = requestsService.getNewRequest(user, typeOfItem, content,1);
            exists=requestsService.existCookingStyle(cookingStylesList,request);
            status=true;

            //function to verify if the cookingstyle already exists.
            requestsService.saveItem(request,exists);
        }else if (requestsService.isCategory(typeOfItem)) {
            request = requestsService.getNewRequest(user, typeOfItem, content,2);
            exists=requestsService.existCategory(categoriesList,request);
            status=true;
            requestsService.saveItem(request,exists);
        }else {
            System.out.println("SELECT A TYPE OF REQUEST ITEM !!");
        }
        if (status) {
			return new ResponseEntity<>(request, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    @JsonView(RequestRestController.PostItem.class)
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
        boolean actionAccepted=requestsService.actionIsAccepted(action);
        boolean actionDecline=requestsService.actionIsDecline(action);
        if(actionAccepted){
            if(requestsService.isEqualIngredient(typeOfRequest)){
                requestsService.addItem(0, itemContent, id_request);
                status=true;
                //add ingrediente
            }else if(requestsService.isEqualCategory(typeOfRequest)){
                requestsService.addItem(1, itemContent, id_request);
                status=true;
                //add categoria
            }else if(requestsService.isEqualCookingStyle(typeOfRequest)){
                requestsService.addItem(2, itemContent, id_request);
                status=true;
                //add cookingStyle
            }
            //if we accept the item we have to follow a serue of functions to put it right
        }else if(actionDecline){
            requestsService.declineItem(id_request);
            status=true;
            //we delete the item through his id
        }
        Page<Request> request = requestsService.getRequests(page_number,page_size);
        List<Request> requestList = (List<Request>)request.getContent();
        if (status) {
            //String response_="the item was "+typeOfRequest+" and has been "+action;
			return new ResponseEntity<>(requestList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }



}