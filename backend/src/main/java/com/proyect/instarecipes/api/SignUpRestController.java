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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/signUp")
public class SignUpRestController{
    // public interface fastReg extends User.fastRegister{}
    
    @Autowired 
    UsersRepository usersRepository;

    // @JsonView(SignUpRestController.fastReg.class)
    @PostMapping(value = "signUp/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> aulaVirtual( String username, String email, String password, String name, String surname , HttpServletRequest request){
        User user = new User(username, email, password, name, surname, null, null, null, null, "ROLE_USER");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }    
}
