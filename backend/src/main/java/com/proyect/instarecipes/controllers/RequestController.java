package com.proyect.instarecipes.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RequestsRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestController {

    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private CookingStylesRepository cookingStylesRepository;

    @Autowired
    private UserSession userSession;

    @PostMapping("/sendItemRequest")
    public void sentItemRequest(@RequestParam("typeOfItem") String typeOfItem, @RequestParam("content") String content,
            HttpServletResponse response) {
        User user = userSession.getLoggedUser();
        Request request;
        boolean exists = false;
        List<Ingredient> ingredientsList = ingredientsRepository.findAll();
        List<Category> categoriesList = categoriesRepository.findAll();
        List<CookingStyle> cookingStylesList = cookingStylesRepository.findAll();
        if (typeOfItem.contains("Ingredient")) {
            request = new Request(user, typeOfItem, content, null, null, false);
            for(Ingredient ingredient : ingredientsList){
                if(ingredient.getIngredient().equalsIgnoreCase(request.getIngredientContent())){
                    exists = true;
                    break;
                }
            }
            request.setItemExists(exists);
            requestsRepository.save(request);
        }else if (typeOfItem.contains("Cooking style")) {
            request = new Request(user, typeOfItem, null, content, null, false);
            for(CookingStyle cookingStyle : cookingStylesList){
                if(cookingStyle.getCookingStyle().equalsIgnoreCase(request.getCookingStyleContent())){
                    exists = true;
                    break;
                }
            }
            request.setItemExists(exists);
            requestsRepository.save(request);
        }else if (typeOfItem.contains("Category")) {
            request = new Request(user, typeOfItem, null, null, content, false);
            for(Category category : categoriesList){
                if(category.getCategory().equalsIgnoreCase(request.getCategoryContent())){
                    exists = true;
                    break;
                }
            }
            request.setItemExists(exists);
            requestsRepository.save(request);
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
        if(action.equalsIgnoreCase("accept")){
            if(typeOfRequest.equalsIgnoreCase("Ingredient")){
                System.out.println("INGREDIENT ADDED");
                Ingredient i = new Ingredient(itemContent);
                ingredientsRepository.save(i);
                requestsRepository.deleteById(id_request);
            }else if(typeOfRequest.equalsIgnoreCase("Category")){
                Category i = new Category(itemContent);
                System.out.println("CATEGORY ADDED");
                categoriesRepository.save(i);
                requestsRepository.deleteById(id_request);
            }else if(typeOfRequest.equalsIgnoreCase("Cooking Style")){
                System.out.println("COOKING STYLE ADDED");
                CookingStyle i = new CookingStyle(itemContent);
                cookingStylesRepository.save(i);
                requestsRepository.deleteById(id_request);
            }
        }else if(action.equalsIgnoreCase("decline")){
            System.out.println("DECLINED");
            requestsRepository.deleteById(id_request);
        }
        try {
            response.sendRedirect("profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @ModelAttribute
    // public void modelPage(Model model) {
    //     boolean logged = userSession.getLoggedUser() != null;
    //     model.addAttribute("logged", logged);
	// 	if(logged){
	// 		model.addAttribute("user",userSession.getLoggedUser().getUsername());
	// 		model.addAttribute("admin", userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
	// 	}
    //     //if(userSession.getLoggedUser().getRoles().contains("ROLE_ADMIN")){
    //         model.addAttribute("allRequests", requestsRepository.findAll());
    //     //}
    // }
    

}