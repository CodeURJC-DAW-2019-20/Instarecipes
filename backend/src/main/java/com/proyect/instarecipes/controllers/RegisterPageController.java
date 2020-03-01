package com.proyect.instarecipes.controllers;

import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.ImageService;
import com.proyect.instarecipes.security.UserAuthProvider;
import com.proyect.instarecipes.security.UserSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegisterPageController {

    @Autowired   
    private UsersRepository usersRepository;
    @Autowired
    public UserAuthProvider userAuthProvider;
    @Autowired
    private ImageService imageService;
    @PostMapping("/signUp")
    public void signUp(Model model, User user, HttpServletRequest request, HttpServletResponse response,
            @RequestParam MultipartFile fileAvatar) throws IOException {
        // cause i need to transform password hash and set role of user
        User u = new User(user.getUsername(), user.getEmail(), user.getPassword(), user.getName(), user.getSurname(),
                "Hello world !!", user.getAllergens(), user.getFollowers(), user.getFollowing(), "ROLE_USER");

        boolean userExists = usersRepository.findByUsername(u.getUsername()) != null
                || usersRepository.findByEmail(u.getEmail()) != null;
        if (userExists) {
            System.out.println("User already registered !!");
            try {
                response.sendRedirect("signUp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            u.setAvatar(true);
            usersRepository.save(u);
            File defaultBackground = new File("src/main/resources/static/images/backgrounds/background2.jpg");
            FileInputStream inputB = new FileInputStream(defaultBackground);
            MultipartFile fileBackground = new MockMultipartFile("file2", defaultBackground.getName(), "image/jpeg",
                    IOUtils.toByteArray(inputB));
            if (fileAvatar == null) {
                File defaultAvatar = new File(
                        "src/main/resources/static/images/backgrounds/profile_background_example.jpeg");
                FileInputStream inputA = new FileInputStream(defaultBackground);
                fileAvatar = new MockMultipartFile("file2", defaultAvatar.getName(), "image/jpeg",
                        IOUtils.toByteArray(inputA));
            }
            imageService.saveImage("avatars", u.getId(), fileAvatar);
            imageService.saveImage("backgrounds", u.getId(), fileBackground);
            try {
                authenticateUser(u.getUsername(), user.getPassword(), request);
                // THIS IS THE FKING KEY
                response.sendRedirect("index");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

  

    // this method aproach the @Autowired of userAuthProvider to autenticate user
    // and password and setup autologged
    private void authenticateUser(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        request.getSession();
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = userAuthProvider.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}