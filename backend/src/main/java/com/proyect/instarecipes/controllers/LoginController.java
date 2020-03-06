package com.proyect.instarecipes.controllers;

import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	public RegisterPageController registerPagerController;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UserSession userComponent;
	
	private int countUsers = 1;

	@RequestMapping("/login")
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

	@RequestMapping("/logout")
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

	@PostMapping("/googleUser")
	public void loginGoogleUser(Model model, User user, HttpServletResponse response, HttpServletRequest request, @RequestParam String fullNameGU,
			@RequestParam String givenNameGU, @RequestParam String familyNameGU, @RequestParam String emailGU) throws IOException {
		List<String> roleUser = new ArrayList<>();
		roleUser.add("ROLE_USER");
		//does user exist?							
		boolean userExists = usersRepository.findByEmail(emailGU) != null;
		if (!userExists) {
			User googleUser = new User();
			//IMAGE
			File fileGoogle = new File("src/main/resources/static/images/icons/google-image.jpg");
			FileInputStream inputGoogle = new FileInputStream(fileGoogle);
			MultipartFile imageFile2 = new MockMultipartFile("fileGoogle", fileGoogle.getName(), "image/jpg", IOUtils.toByteArray(inputGoogle));;
			//SET INFO BECAUSE THE USER IS NEW!
			googleUser.setName(givenNameGU);
			googleUser.setSurname(familyNameGU);
			googleUser.setEmail(emailGU);
			googleUser.setRoles(roleUser);
			googleUser.setAllergens("Nuts");
			googleUser.setAvatar(true);
			//CHECK IF THE USERNAME CREATE BY NAME + FIRST SURNAME EXISTS
			String[] firstSurname = familyNameGU.trim().split("\\s+");
			String tryUsername = (givenNameGU + firstSurname[0]).toLowerCase();
			if (usersRepository.findByUsername(tryUsername) == null) { //if doesn't exists in our database, we add it 
				googleUser.setUsername(tryUsername);
			} else {		
				String sumInt= Integer.toString(countUsers);
       			String tryUsername2 = tryUsername + sumInt;
				googleUser.setUsername(tryUsername2); //add to avoid possible future errors :)
				countUsers++;
			}
			googleUser.setAvatar(false);
			googleUser.setBackground(false);
			//GENERATE A RANDOM PASSWORD 
			googleUser.setPassword(getSaltString());
			registerPagerController.signUp(model, googleUser, request, response, imageFile2);
			//imageService.saveImage("avatars", googleUser.getId(), imageFile2);
		}
	}

	protected String getSaltString() {
        String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder jump = new StringBuilder();
        Random rnd = new Random();
        while (jump.length() < 18) { // length of the random password.
            int index = (int) (rnd.nextFloat() * availableChars.length());
            jump.append(availableChars.charAt(index));
        }
        String saltStr = jump.toString();
        return saltStr;

    }
}