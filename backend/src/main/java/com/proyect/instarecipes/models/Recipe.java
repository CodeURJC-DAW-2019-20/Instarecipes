package com.proyect.instarecipes.models;
import java.util.concurrent.atomic.AtomicInteger;
// import java.awt.Image;

public class Recipe{
    private AtomicInteger id;
    private String username;
    private int id_ingredients;
    private String name_categories;
    private String name_cookingStyle;
    private String title;
    private String description;
    private String duration;
    private String dificulty;
    private String steps;
    // private Image galery;

    //Empty contructor
    public Recipe(){}

    //Constructor with all atributes
    public Recipe(AtomicInteger id, String username, int id_ingredients, String name_categories,
    String name_cookingStyle, String title, String description, String duration, String dificulty, String steps) {
        this.id = id;
        this.username = username;
        this.id_ingredients = id_ingredients;
        this.name_categories = name_categories;
        this.name_cookingStyle = name_cookingStyle;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.dificulty = dificulty;
        this.steps = steps;
        //this.galery = galery;
    } 

    //GETTERS AND SETTERS

    public AtomicInteger getId() {
        return id;
    }

    public void setId(AtomicInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId_ingredients() {
        return id_ingredients;
    }

    public void setId_ingredients(int id_ingredients) {
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

    public String getDificulty() {
        return dificulty;
    }

    public void setDificulty(String dificulty) {
        this.dificulty = dificulty;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    // public Image getGalery() {
    //     return galery;
    // }

    // public void setGalery(Image galery) {
    //     this.galery = galery;
    // }

}