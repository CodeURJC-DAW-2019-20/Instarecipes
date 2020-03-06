package com.proyect.instarecipes.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public interface showNewUser extends User.NameSurname, User.Username, User.allergen, User.email{}
    @Autowired 
    UsersRepository usersRepository;

    @JsonView(SignUpRestController.showNewUser.class)
    @RequestMapping("/signUp")
    public ResponseEntity<User> aulaVirtual(@RequestParam String username,@RequestParam String email, @RequestParam String password,@RequestParam  String name,
    @RequestParam String surname,@RequestParam String allergen, HttpServletRequest request){
        User user = new User(username, email, password, name, surname, "this is my info", allergen, null, null, "ROLE_USER");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }    
}
