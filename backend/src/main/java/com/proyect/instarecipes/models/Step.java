package com.proyect.instarecipes.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Step{

    public interface Simple{}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_step;

    @ManyToOne
    private Recipe recipe;
    
    @JsonView(Simple.class)
    private int number;

    @JsonView(Simple.class)
    @Column(length = 500)
    private String content;

    @JsonView(Simple.class)
    private boolean image;

    public Step(){
    }
    
    public Step(Recipe recipe,int number, String contect){
        this.recipe=recipe;
        this.number=number;
        this.content=contect;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Long getId_step() {
        return id_step;
    }

    public void setId_step(Long id_step) {
        this.id_step = id_step;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

}