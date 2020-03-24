package com.proyect.instarecipes.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;


@RestController
@RequestMapping("/api")
public class SignUpRestController{
    public interface showNewUser extends User.NameSurname, User.Username, User.Allergen, User.Email, User.UserExtraInfo, User.FF{}
    @Autowired 
    UsersRepository usersRepository;

    // SIGN UP A NEW USER
    @JsonView(SignUpRestController.showNewUser.class)
    @RequestMapping("/signup")
    public ResponseEntity<User> SignUp(@RequestBody User user1)throws IOException{ 
        ArrayList<User> users = new ArrayList<User>(usersRepository.findAll());
        boolean exist = false;
        Set<User> follow = new HashSet<>();
        System.out.println(user1.getUsername());
        for(User u : users){
            if(u.getUsername().equalsIgnoreCase(user1.getUsername())){
                exist = true;
                break;
            }
        }       
        if(!exist){
            User user = new User(user1.getUsername(), user1.getEmail(),user1.getPassword(),user1.getName(),user1.getSurname(), user1.getInfo(), user1.getAllergens(), follow, follow, "ROLE_USER");
            File backgroundBots = new File("src/main/resources/static/images/backgrounds/profile_background_example.jpeg");
            FileInputStream bBots = new FileInputStream(backgroundBots);
            MultipartFile userBackground = new MockMultipartFile("file3", backgroundBots.getName(), "image/jpeg", IOUtils.toByteArray(bBots));
            user.setBackground(false);
            user.setImageBackground(userBackground.getBytes());
            usersRepository.save(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
