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
import com.proyect.instarecipes.service.RequestsService;

import org.springframework.beans.factory.annotation.Autowired;
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
    private RequestsService requestsService;

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
        }else if (typeOfItem.contains("Category")) {
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



}