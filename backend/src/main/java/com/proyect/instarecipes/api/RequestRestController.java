package com.proyect.instarecipes.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.repositories.AllergensRepository;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RequestRestController {

    public interface PostItem extends Request.RequestItems{}

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
        //funcion extraer ingredientes,categorias y cookingStyle user request
        if (requestsService.isIngredient(typeOfItem)) {
            request = requestsService.getNewRequest(user, typeOfItem,content);
            exists=requestsService.existIngredient(ingredientsList,request);
            status=true;
            //funcion comprobando si existe la receta colocarla en service
            requestsService.saveItem(request,exists);
        }else if (requestsService.isCookingStyle(typeOfItem)) {
            request = requestsService.getNewRequest(user, typeOfItem, content);
            exists=requestsService.existCookingStyle(cookingStylesList,request);
            status=true;

            //funcion comprobando si existe la cookingStyle colocarla en servic
            requestsService.saveItem(request,exists);
        }else if (requestsService.isCategory(typeOfItem)) {
            request = requestsService.getNewRequest(user, typeOfItem, content);
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
                //añadir ingrediente
            }else if(requestsService.isEqualCategory(typeOfRequest)){
                requestsService.addItem(1, itemContent, id_request);
                status=true;
                //añadir categoria
            }else if(requestsService.isEqualCookingStyle(typeOfRequest)){
                requestsService.addItem(2, itemContent, id_request);
                status=true;
                //añadir cookingStyle
            }
            //si aceptamos el item tenemos que seguir una serie de funciones para añadirlo en 
            //su correspondiente sitio
        }else if(actionDecline){
            requestsService.declineItem(id_request);
            status=true;
            //eliminamos el item a traves del id
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