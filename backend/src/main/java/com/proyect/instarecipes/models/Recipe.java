package com.proyect.instarecipes.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Recipe{

    public interface IndexView{}
    public interface RecipeView{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Index Views
    @JsonView(IndexView.class)
    @ManyToOne
    private User username;
    @JsonView(IndexView.class)
    private String title;
    @JsonView(IndexView.class)
    private String description;
    @JsonView(IndexView.class)
    private int likes;
    @JsonView(IndexView.class)
    private int n_comments;

    @ManyToMany
    private Set<CookingStyle> cookingStyles;
    @ManyToMany
    private Set<Allergen> allergens;

    @ManyToMany
    private Set<User> likesUsers;

    //Recipe Views
    @JsonView(RecipeView.class)
    @ManyToMany
    private Set<Ingredient> ingredients;
    @JsonView(RecipeView.class)
    @ManyToMany
    private Set<Category> categories;
    @JsonView(RecipeView.class)
    private String duration;
    @JsonView(RecipeView.class)
    private String difficulty;
    @JsonView(RecipeView.class)
    private boolean image;

    //Empty contructor
    public Recipe(){}

    //Constructor with all atributes
    public Recipe(User username, Set<Ingredient> ingredients, Set<Category> categories, Set<CookingStyle> cookingStyles,
    Set<Allergen> allergens, String title, String description, String duration, String difficulty, Set<User> likesUsers) {
        this.username = username;
        this.ingredients = ingredients;
        this.categories = categories;
        this.cookingStyles = cookingStyles;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.difficulty = difficulty;
        this.allergens = allergens;
        this.likesUsers = likesUsers;
        this.likes= likesUsers.size();
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

    public Set<Allergen> getAllergens() {
        return this.allergens;
    }

    public void setAllergens(Set<Allergen> alergens) {
        this.allergens = alergens;
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

    public void setImage(boolean image){
        this.image=image;
    }
    public boolean getImage(){
        return this.image;
    }
    public int getN_comments() {
        return n_comments;
    }

    public void setN_comments(int n_comments) {
        this.n_comments = n_comments;
    }

	public Set<User> getLikesUsers() {
		return likesUsers;
	}
	public void setLikesUsers(Set<User> likesUsers) {
		this.likesUsers = likesUsers;
	}

    public int getLikes() {
        likes = likesUsers.size();
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void addUser(User u){
        likesUsers.add(u);
        likes = likesUsers.size();
    }
    public void removeUser(User u){
        likesUsers.remove(u);
        likes= likesUsers.size();
    }

}