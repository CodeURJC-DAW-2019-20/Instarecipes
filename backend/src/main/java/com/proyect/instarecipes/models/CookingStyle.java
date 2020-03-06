package com.proyect.instarecipes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class CookingStyle{
    public interface Item{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonView(Item.class)
    private String cookingStyle;

    public CookingStyle() {
    }

    public CookingStyle(String cookingStyle) {
        this.cookingStyle = cookingStyle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCookingStyle() {
        return cookingStyle;
    }

    public void setCookingStyle(String cookingStyle) {
        this.cookingStyle = cookingStyle;
    }
    
}