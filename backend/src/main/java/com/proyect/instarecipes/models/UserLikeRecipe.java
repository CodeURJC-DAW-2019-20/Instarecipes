package com.proyect.instarecipes.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class UserLikeRecipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User username;
    @ManyToMany(mappedBy = "username")
    Set<Recipe> recipe;

    private boolean checked;
    
    public UserLikeRecipe() {
    }
    
    public UserLikeRecipe(User username, Recipe recipe, boolean checked) {
        this.username = username;
        this.recipe.add(recipe);
        this.checked = checked;    
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

    public Set<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe.add(recipe);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
