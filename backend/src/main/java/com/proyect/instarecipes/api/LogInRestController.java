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
@RequestMapping("/api")
public class LogInRestController {
	public interface ShowUser
			extends User.NameSurname, User.UserExtraInfo, User.Username, User.Allergen, User.Email, User.FF {}

	@Autowired
	private UserSession userSession;
	private static final Logger log = LoggerFactory.getLogger(LogInRestController.class);

	// LOGIN
	@JsonView(LogInRestController.ShowUser.class)
    @RequestMapping("/login")
	public ResponseEntity<User> logIn() {
		if (!userSession.isLoggedUser()) {
			log.info("NOT LOGGED USER");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else{
			User loggedUser = userSession.getLoggedUser();
			log.info("Logged as " + loggedUser.getName());
			return new ResponseEntity<>(loggedUser, HttpStatus.OK);
		}
	}
	
	// LOGOUT
	@RequestMapping("/logout")
	public ResponseEntity<Boolean> logOut(HttpSession session) {
		if (!userSession.isLoggedUser()) {
			log.info("No user logged");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}else{
			session.invalidate();
			log.info("Logged out");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
	}
    
}