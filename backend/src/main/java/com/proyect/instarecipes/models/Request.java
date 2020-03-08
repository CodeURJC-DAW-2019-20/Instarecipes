package com.proyect.instarecipes.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Request{

    public interface RequestItems  {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonView(RequestItems.class)
    @OneToOne
    private User username;
    @JsonView(RequestItems.class)
    @Column(nullable = true)
    private String typeOfRequest;
    @JsonView(RequestItems.class)
    @Column(nullable = true)
    private String ingredientContent;
    @JsonView(RequestItems.class)
    @Column(nullable = true)
    private String cookingStyleContent;
    @JsonView(RequestItems.class)
    @Column(nullable = true)
    private String categoryContent;
    @JsonView(RequestItems.class)
    @Column(nullable = true)
    private boolean itemExists;

    // @Column(nullable = true)
    // private Recipe recipeReport;
    // @Column(nullable = true)
    // private User userReport;

    public Request() {
    }

    public Request(User username, String typeOfRequest, String ingredientContent, String cookingStyleContent,
            String categoryContent, boolean itemExists) {
        this.username = username;
        this.typeOfRequest = typeOfRequest;
        if(ingredientContent == null){
            this.ingredientContent = "";
        }else{
            this.ingredientContent = ingredientContent;
        }
        if(cookingStyleContent == null){
            this.cookingStyleContent = "";
        }else{
            this.cookingStyleContent = cookingStyleContent;
        }
        if(categoryContent == null){
            this.categoryContent = "";
        }else{
            this.categoryContent = categoryContent;
        }
        this.itemExists = itemExists;
        // this.recipeReport = recipeReport;
        // this.userReport = userReport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getTypeOfRequest() {
        return typeOfRequest;
    }

    public void setTypeOfRequest(String typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public String getIngredientContent() {
        return ingredientContent;
    }

    public void setIngredientContent(String ingredientContent) {
        this.ingredientContent = ingredientContent;
    }

    public String getCookingStyleContent() {
        return cookingStyleContent;
    }

    public void setCookingStyleContent(String cookingStyleContent) {
        this.cookingStyleContent = cookingStyleContent;
    }

    public String getCategoryContent() {
        return categoryContent;
    }

    public void setCategoryContent(String categoryContent) {
        this.categoryContent = categoryContent;
    }

    // public Request(User username, String typeOfRequest, String ingredientContent, String cookingStyleContent,
    //         String categoryContent, boolean itemExists) {
    //     this.username = username;
    //     this.typeOfRequest = typeOfRequest;
    //     this.ingredientContent = ingredientContent;
    //     this.cookingStyleContent = cookingStyleContent;
    //     this.categoryContent = categoryContent;
    //     this.itemExists = itemExists;
    // }

    public boolean isItemExists() {
        return itemExists;
    }

    public void setItemExists(boolean itemExists) {
        this.itemExists = itemExists;
    }

    // public Recipe getRecipeReport() {
    //     return recipeReport;
    // }

    // public void setRecipeReport(Recipe recipeReport) {
    //     this.recipeReport = recipeReport;
    // }

    // public User getUserReport() {
    //     return userReport;
    // }

    // public void setUserReport(User userReport) {
    //     this.userReport = userReport;
    // }

    
}