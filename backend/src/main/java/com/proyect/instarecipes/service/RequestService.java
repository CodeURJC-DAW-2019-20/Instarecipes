package com.proyect.instarecipes.service;

import java.util.List;

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
import org.springframework.stereotype.Service;

@Service
public class RequestService {

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

    public User getUser() {
        return userSession.getLoggedUser();
    }

    public List<Ingredient> getIngredients() {
        return ingredientsRepository.findAll();
    }

    public List<Category> getCategories() {
        return categoriesRepository.findAll();
    }

    public List<CookingStyle> getCookingStyles() {
        return cookingStylesRepository.findAll();
    }

    public boolean isEqualIngredient(String typeOfRequest) {
        return typeOfRequest.equals("Ingredient");
    }

    public boolean isEqualCategory(String typeOfRequest) {
        return typeOfRequest.equals("Category");
    }

    public boolean isEqualCookingStyle(String typeOfRequest) {
        return typeOfRequest.equals("Cooking style");
    }

    public Request getNewRequest(User user, String typeOfItem, String content, int caso) {
        Request request = null;
        switch (caso) {
            case 0:
                request = new Request(user, typeOfItem, content, "", "", false);
                break;
            case 1:
                request = new Request(user, typeOfItem, "", content, "", false);
                break;
            case 2:
                request = new Request(user, typeOfItem, "", "", content, false);
                break;
        }
        return request;
    }

    public boolean existIngredient(List<Ingredient> ingredientsList, String request) {
        boolean exists = false;
        for (Ingredient ingredient : ingredientsList) {
            if (ingredient.getIngredient().equalsIgnoreCase(request)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean existCategory(List<Category> categoryList, String request) {
        boolean exists = false;
        for (Category category : categoryList) {
            if (category.getCategory().equalsIgnoreCase(request)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean existCookingStyle(List<CookingStyle> cookingStyles, String request) {
        boolean exists = false;
        for (CookingStyle cookingStyle : cookingStyles) {
            if (cookingStyle.getCookingStyle().equalsIgnoreCase(request)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public void saveItem(Request request, boolean exists) {
        request.setItemExists(exists);
        requestsRepository.save(request);
    }

    public boolean actionIsAccepted(String action) {
        return action.equalsIgnoreCase("accept");
    }

    public boolean actionIsDecline(String action) {
        return action.equalsIgnoreCase("decline");
    }

    public void addItem(int type, String itemContent, long id_request) {
        switch (type) {
            case 0:
                ingredientsRepository.save(new Ingredient(itemContent));
                requestsRepository.deleteById(id_request);
                break;
            case 1:
                categoriesRepository.save(new Category(itemContent));
                requestsRepository.deleteById(id_request);
                break;
            case 2:
                cookingStylesRepository.save(new CookingStyle(itemContent));
                requestsRepository.deleteById(id_request);
                break;
        }
    }

    public void declineItem(long id_request) {
        System.out.println("DECLINED");
        requestsRepository.deleteById(id_request);
    }

    public List<Request> getRequests() {
        return requestsRepository.findAll();
    }
}