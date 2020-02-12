package com.proyect.instarecipes.ingredients;
import java.util.concurrent.atomic.AtomicInteger;

public class Ingredient {
    private AtomicInteger id;
    private String name;
    private String name_categories;
    private String allergens;

    public AtomicInteger getId() {
        return this.id;
    }

    public void setId(AtomicInteger id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_categories() {
        return this.name_categories;
    }

    public void setName_categories(String name_categories) {
        this.name_categories = name_categories;
    }

    public String getAllergens() {
        return this.allergens;
    }

    public void setAllergens(String alergens) {
        this.allergens = alergens;
    }

}