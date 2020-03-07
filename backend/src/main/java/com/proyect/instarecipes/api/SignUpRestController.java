package com.proyect.instarecipes.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api")
public class SignUpRestController{
    public interface showNewUser extends User.NameSurname, User.Username, User.Allergen, User.Email, User.UserExtraInfo, User.FF{}
    @Autowired 
    UsersRepository usersRepository;

    @JsonView(SignUpRestController.showNewUser.class)
    @RequestMapping("/signUp")
    public ResponseEntity<User> aulaVirtual(@RequestParam String username,@RequestParam String email, @RequestParam String password,@RequestParam  String name,
    @RequestParam String surname,@RequestParam String allergen, HttpServletRequest request){
        ArrayList<User> users = new ArrayList<User>(usersRepository.findAll());
        boolean exist = false;
        Set<User> follow = new HashSet<>();
        User user = new User(username, email, password, name, surname, "this is my info", allergen, follow, follow, "ROLE_USER");
        for(User u : users){
            if(u.getUsername().equalsIgnoreCase(user.getUsername())){
                exist = true;
                break;
            }
        }
        if(!exist){
            usersRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }    
}
