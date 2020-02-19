package com.proyect.instarecipes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Request{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String type;
    private String value;

    @OneToOne
    private User user;

    public Request() {
    }

    public Request(String type, String value, User user) {
        this.type = type;
        this.value = value;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}