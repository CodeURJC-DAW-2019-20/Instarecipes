package com.proyect.instarecipes.models;
// import java.awt.Image;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Recipe{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @OneToMany
    private Set<Ingredient> id_ingredients;
    @OneToMany
    private Set<Step> steps;

    private String name_categories;
    private String name_cookingStyle;
    private String title;
    private String description;
    private String duration;
    private String difficulty;
    private String allergens;
    private boolean Image;
    // private Image galery;

    //Empty contructor
    public Recipe(){}

    //Constructor with all atributes
    public Recipe(String username, Set<Ingredient> id_ingredients, String name_categories, String name_cookingStyle,
    String title, String description, String duration, String difficulty, Set<Step> steps, String allergens) {
        this.username = username;
        this.id_ingredients = id_ingredients;
        this.name_categories = name_categories;
        this.name_cookingStyle = name_cookingStyle;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.difficulty = difficulty;
        this.steps = steps;
        this.allergens = allergens;
    }
    //GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Ingredient> getId_ingredients() {
        return id_ingredients;
    }

    public void setId_ingredients(Set<Ingredient> id_ingredients) {
        this.id_ingredients = id_ingredients;
    }

    public String getName_categories() {
        return name_categories;
    }

    public void setName_categories(String name_categories) {
        this.name_categories = name_categories;
    }

    public String getName_cookingStyle() {
        return name_cookingStyle;
    }

    public void setName_cookingStyle(String name_cookingStyle) {
        this.name_cookingStyle = name_cookingStyle;
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

    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    public String getAllergens() {
        return this.allergens;
    }

    public void setAllergens(String alergens) {
        this.allergens = alergens;
    }
    public void setImage(boolean Image){
        this.Image=Image;
    }

    // public Image getGalery() {
    //     return galery;
    // }

    // public void setGalery(Image galery) {
    //     this.galery = galery;
    // }

}