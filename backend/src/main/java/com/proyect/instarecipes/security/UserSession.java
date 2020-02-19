package com.proyect.instarecipes.security;

import com.proyect.instarecipes.models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {

    private User user;

    public User getLoggedUser() {
        return user;
    }

    public void setLoggedUser(User user) {
        this.user = user;
    }

    public boolean isLoggedUser() {
        return this.user != null;
    }

}
