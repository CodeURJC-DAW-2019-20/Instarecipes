package com.proyect.instarecipes.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Recipe{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User username;

    @ManyToMany
    private Set<Ingredient> ingredients;
    @ManyToMany
    private Set<Category> categories;
    @ManyToMany
    private Set<CookingStyle> cookingStyles;
    @ManyToMany
    private Set<Allergen> allergens;

    private String title;
    private String description;
    private String duration;
    private String difficulty;

    private int likes;
    private int n_comments;
    // private Image galery;

    //Empty contructor
    public Recipe(){}

    //Constructor with all atributes
    public Recipe(User username, Set<Ingredient> ingredients, Set<Category> categories, Set<CookingStyle> cookingStyles,
    Set<Allergen> allergens, String title, String description, String duration, String difficulty, int likes) {
        this.username = username;
        this.ingredients = ingredients;
        this.categories = categories;
        this.cookingStyles = cookingStyles;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.difficulty = difficulty;
        this.allergens = allergens;
        this.likes = likes;
    }
    //GETTERS AND SETTERS

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

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<CookingStyle> getCookingStyles() {
        return cookingStyles;
    }

    public void setCookingStyles(Set<CookingStyle> cookingStyles) {
        this.cookingStyles = cookingStyles;
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

    public Set<Allergen> getAllergens() {
        return this.allergens;
    }

    public void setAllergens(Set<Allergen> alergens) {
        this.allergens = alergens;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getN_comments() {
        return n_comments;
    }

    public void setN_comments(int n_comments) {
        this.n_comments = n_comments;
    }

}