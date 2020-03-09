package com.proyect.instarecipes.api;

public class FilteredSearchDTO {
    private String ingredients;
    private String categories;
    private String cookingStyles;
    private String allergens;

    public FilteredSearchDTO(String ingredients, String categories, String cookingStyles, String allergens) {
        this.ingredients = ingredients;
        this.categories = categories;
        this.cookingStyles = cookingStyles;
        this.allergens = allergens;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCookingStyles() {
        return cookingStyles;
    }

    public void setCookingStyles(String cookingStyles) {
        this.cookingStyles = cookingStyles;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

}
