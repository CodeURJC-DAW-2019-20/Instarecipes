package com.proyect.instarecipes.views.DTO;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.User;

public class RecipeDTO {
    public interface PostRecipeView {}

    @JsonView(PostRecipeView.class)
    private User user;
    @JsonView(PostRecipeView.class)
    private String title;
    @JsonView(PostRecipeView.class)
    private String description;
    @JsonView(PostRecipeView.class)
    private String duration;
    @JsonView(PostRecipeView.class)
    private String difficulty;
    @JsonView(PostRecipeView.class)
    private String firstStep;
    @JsonView(PostRecipeView.class)
    private String allergen;
    @JsonView(PostRecipeView.class)
    private ArrayList<String> withImage;
    @JsonView(PostRecipeView.class)
    private ArrayList<String> steps;
    @JsonView(PostRecipeView.class)
    private ArrayList<String> ingredients;
    @JsonView(PostRecipeView.class)
    private ArrayList<String> categories;
    @JsonView(PostRecipeView.class)
    private ArrayList<String> cookingStyles;

    public RecipeDTO(User user, String title, String description, String duration, String difficulty, String firstStep,
            String allergen, ArrayList<String> withImage, ArrayList<String> steps, ArrayList<String> ingredients,
            ArrayList<String> categories, ArrayList<String> cookingStyles) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.difficulty = difficulty;
        this.firstStep = firstStep;
        this.allergen = allergen;
        this.withImage = withImage;
        this.steps = steps;
        this.ingredients = ingredients;
        this.categories = categories;
        this.cookingStyles = cookingStyles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getFirstStep() {
        return firstStep;
    }

    public void setFirstStep(String firstStep) {
        this.firstStep = firstStep;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public ArrayList<String> getWithImage() {
        return withImage;
    }

    public void setWithImage(ArrayList<String> withImage) {
        this.withImage = withImage;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getCookingStyles() {
        return cookingStyles;
    }

    public void setCookingStyles(ArrayList<String> cookingStyles) {
        this.cookingStyles = cookingStyles;
    }
    
}