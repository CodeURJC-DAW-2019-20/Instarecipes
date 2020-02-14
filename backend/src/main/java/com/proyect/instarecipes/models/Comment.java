package com.proyect.instarecipes.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;
    
    private String content;

    @OneToMany
    private Set<Comment> subcomment;

    private long likes;

    public Comment() {}

    public Comment(long id, User user, String content, Set<Comment> subcomment, long likes) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.subcomment = subcomment;
        this.likes = likes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Set<Comment> getSubcomment() {
        return subcomment;
    }

    public void setSubcomment(Set<Comment> subcomment) {
        this.subcomment = subcomment;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }
    
    
}