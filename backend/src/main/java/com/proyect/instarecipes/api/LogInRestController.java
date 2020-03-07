package com.proyect.instarecipes.api;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.proyect.instarecipes.security.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
public class LogInRestController{
    public interface ShowUser extends User.NameSurname, User.UserExtraInfo, User.Username, User.allergen, User.email{} 
    
    @Autowired
    private UserSession userComponent;

    private static final Logger log = LoggerFactory.getLogger(LogInRestController.class);

    @JsonView(LogInRestController.ShowUser.class)
    @RequestMapping("/api/login")
	public ResponseEntity<User> logIn() {
		if (!userComponent.isLoggedUser()) {
			log.info("Not user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			User loggedUser = userComponent.getLoggedUser();
			log.info("Logged as " + loggedUser.getName());
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);
		}
	}
	@RequestMapping("/api/logout")
	public ResponseEntity<Boolean> logOut(HttpSession session) {
		if (!userComponent.isLoggedUser()) {
			log.info("No user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			session.invalidate();
			log.info("Logged out");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}
    
}