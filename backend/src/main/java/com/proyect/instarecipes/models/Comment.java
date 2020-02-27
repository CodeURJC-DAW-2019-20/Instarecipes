package com.proyect.instarecipes.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User userComment;
    
    @Column(nullable = false)
    private String content;

    private boolean hasSubcomments;
    private boolean isSubcomment;

    @OneToMany
    private Set<Comment> subComments;

    @ManyToOne
    private Recipe recipe;

    private long likes;

    public Comment() {}

    public Comment(User userComment, String content, Set<Comment> subComments, long likes, Recipe recipe, boolean hasSubcomments, boolean isSubcomment) {
        this.userComment = userComment;
        this.content = content;
        this.subComments = subComments;
        this.likes = likes;
        this.recipe = recipe;
        this.hasSubcomments = hasSubcomments;
        this.isSubcomment = isSubcomment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserComment() {
        return userComment;
    }

    public void setUserComment(User userComment) {
        this.userComment = userComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Set<Comment> getSubComments() {
        return subComments;
    }

    public void setSubComments(Set<Comment> subComments) {
        this.subComments = subComments;
    }

    public boolean isHasSubcomments() {
        return hasSubcomments;
    }

    public void setHasSubcomments(boolean hasSubcomments) {
        this.hasSubcomments = hasSubcomments;
    }

    public boolean isSubcomment() {
        return isSubcomment;
    }

    public void setSubcomment(boolean isSubcomment) {
        this.isSubcomment = isSubcomment;
    }
    
}