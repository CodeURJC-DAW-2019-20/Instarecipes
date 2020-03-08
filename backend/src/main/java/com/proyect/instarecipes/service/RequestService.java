package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.Comment;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.CategoriesRepository;
import com.proyect.instarecipes.repositories.CommentsRepository;
import com.proyect.instarecipes.repositories.CookingStylesRepository;
import com.proyect.instarecipes.repositories.IngredientsRepository;
import com.proyect.instarecipes.repositories.RecipesRepository;
import com.proyect.instarecipes.repositories.RequestsRepository;
import com.proyect.instarecipes.repositories.StepsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public boolean isIngredient(String typeOfItem) {
        return typeOfItem.contains("Ingredient");
    }

    public boolean isCategory(String typeOfItem) {
        return typeOfItem.contains("Category");
    }

    public boolean isCookingStyle(String typeOfItem) {
        return typeOfItem.contains("Cooking style");
    }

    public boolean isEqualIngredient(String typeOfRequest) {
        return typeOfRequest.equalsIgnoreCase("Ingredient");
    }

    public boolean isEqualCategory(String typeOfRequest) {
        return typeOfRequest.equalsIgnoreCase("Category");
    }

    public boolean isEqualCookingStyle(String typeOfRequest) {
        return typeOfRequest.equalsIgnoreCase("Cooking style");
    }

    public Request getNewRequest(User user, String typeOfItem, String content, int caso) {
        Request request = null;

        switch (caso) {
            case 0:
                request = new Request(user, typeOfItem, content, null, null, false);
            case 1:
                request = new Request(user, typeOfItem, null, content, null, false);
            case 2:
                request = new Request(user, typeOfItem, null, null, content, false);
        }
        if (request!=null)
            requestsRepository.save(request);
        return request;
    }

    public boolean existIngredient(List<Ingredient> ingredientsList, Request request) {
        boolean exists = false;
        for (Ingredient ingredient : ingredientsList) {
            if (ingredient.getIngredient().equalsIgnoreCase(request.getIngredientContent())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean existCategory(List<Category> categoryList, Request request) {
        boolean exists = false;
        for (Category category : categoryList) {
            if (category.getCategory().equalsIgnoreCase(request.getCategoryContent())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean existCookingStyle(List<CookingStyle> cookingStyles, Request request) {
        boolean exists = false;
        for (CookingStyle cookingStyle : cookingStyles) {
            if (cookingStyle.getCookingStyle().equalsIgnoreCase(request.getCookingStyleContent())) {
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
                System.out.println("INGREDIENT ADDED");
                Ingredient i = new Ingredient(itemContent);
                ingredientsRepository.save(i);
                requestsRepository.deleteById(id_request);
                break;
            case 1:
                Category j = new Category(itemContent);
                System.out.println("CATEGORY ADDED");
                categoriesRepository.save(j);
                requestsRepository.deleteById(id_request);
                break;
            case 2:
                System.out.println("COOKING STYLE ADDED");
                CookingStyle k = new CookingStyle(itemContent);
                cookingStylesRepository.save(k);
                requestsRepository.deleteById(id_request);
                break;
        }
    }

    public void declineItem(long id_request) {
        System.out.println("DECLINED");
        requestsRepository.deleteById(id_request);
    }

    public Page<Request> getRequests(int page_number, int page_size) {
        return requestsRepository.findAllRequests(PageRequest.of(page_number, page_size));
    }
}