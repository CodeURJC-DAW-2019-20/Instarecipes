package com.proyect.instarecipes.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestWebController {

    @Autowired
    private RequestService requestsService;

    @PostMapping("/sendItemRequest")
    public void sentItemRequest(@RequestParam("typeOfItem") String typeOfItem, @RequestParam("content") String content,
            HttpServletResponse response) {
        User user = requestsService.getUser();
        Request request;
        boolean exists = false;
        List<Ingredient> ingredientsList = requestsService.getIngredients();
        List<Category> categoriesList = requestsService.getCategories();
        List<CookingStyle> cookingStylesList = requestsService.getCookingStyles();
        //funcion extraer ingredientes,categorias y cookingStyle user request
        if (typeOfItem.equals("Ingredient")) {
            request = requestsService.getNewRequest(user, typeOfItem,content,0);
            exists=requestsService.existIngredient(ingredientsList,content);
            requestsService.saveItem(request,exists);
        }else if (typeOfItem.equals("Cooking style")) {
            request = requestsService.getNewRequest(user, typeOfItem, content,1);
            exists=requestsService.existCookingStyle(cookingStylesList,content);
            requestsService.saveItem(request,exists);
        }else if (typeOfItem.equals("Category")) {
            request = requestsService.getNewRequest(user, typeOfItem, content,2);
            exists=requestsService.existCategory(categoriesList,content);
            requestsService.saveItem(request,exists);
        }else {
            System.out.println("SELECT A TYPE OF REQUEST ITEM !!");
        }
        try {
            response.sendRedirect("profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/actionItemRequest")
    public void acceptItemRequest(@RequestParam("typeOfItemRequest") String typeOfRequest, 
    @RequestParam("itemContent") String itemContent,
    @RequestParam("action") String action, 
    @RequestParam("id_request") Long id_request,
    HttpServletResponse response){
        System.out.println("ACTION: "+action);
        System.out.println("Type of request: "+typeOfRequest);
        boolean actionAccepted=requestsService.actionIsAccepted(action);
        boolean actionDecline=requestsService.actionIsDecline(action);
        if(actionAccepted){
            if(requestsService.isEqualIngredient(typeOfRequest)){
                requestsService.addItem(0, itemContent, id_request);
                //añadir ingrediente
            }else if(requestsService.isEqualCategory(typeOfRequest)){
                requestsService.addItem(1, itemContent, id_request);
                //añadir categoria
            }else if(requestsService.isEqualCookingStyle(typeOfRequest)){
                requestsService.addItem(2, itemContent, id_request);
                //añadir cookingStyle
            }
            //si aceptamos el item tenemos que seguir una serie de funciones para añadirlo en 
            //su correspondiente sitio
        }else if(actionDecline){
            requestsService.declineItem(id_request);
            //eliminamos el item a traves del id
        }
        try {
            response.sendRedirect("profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/suspendUsersRequest")
    public void actionUsersRequest(@RequestParam("typeOfItemRequest") String typeOfRequest, 
    @RequestParam("itemContent") String itemContent,
    @RequestParam("action") String action, 
    @RequestParam("id_request") Long id_request,
    HttpServletResponse response){
        
    }

}