package com.proyect.instarecipes.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;
    
    @Column(nullable = false)
    private String content;

    @OneToOne
    private Comment parentAnswer;

    @ManyToOne
    private Recipe recipe;

    private long likes;

    public Comment() {}

    public Comment(User user, String content, Comment parentAnswer, long likes, Recipe recipe) {
        this.user = user;
        this.content = content;
        this.parentAnswer = parentAnswer;
        this.likes = likes;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment getParentAnswer() {
        return parentAnswer;
    }

    public void setParentAnswer(Comment parentAnswer) {
        this.parentAnswer = parentAnswer;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    
}