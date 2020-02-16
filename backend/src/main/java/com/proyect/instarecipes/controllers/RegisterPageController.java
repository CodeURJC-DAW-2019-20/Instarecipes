package com.proyect.instarecipes.controllers;

import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserAuthProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegisterPageController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    public UserAuthProvider userAuthProvider;

    @PostMapping("/signUp")
    public void signUp(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        // cause i need to transform password hash and set role of user
        User u = new User(user.getUsername(), user.getEmail(), user.getPassword(), user.getName(), user.getSurname(),
                user.getAllergens(), user.getFollowers(), user.getFollowing(), "ROLE_USER");

        boolean userExists = usersRepository.findByUsername(u.getUsername()) != null || 
                                usersRepository.findByEmail(u.getEmail()) != null;
        if (userExists) {
            System.out.println("User already registered !!");
            try {
                response.sendRedirect("signUp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            usersRepository.save(u);
            try{
                authenticateUser(u.getUsername(),user.getPassword(),request);
                //THIS IS THE FKING KEY
                response.sendRedirect("index");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    //this method aproach the @Autowird of userAuthProvider to autenticate user and password and setup autologged 
    private void authenticateUser(String username,String password,HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        request.getSession();
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = userAuthProvider.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}