package com.proyect.instarecipes.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Category;
import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;
import com.proyect.instarecipes.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {
    public interface AnotherUserProfile extends Recipe.RecipeBasic, User.NameSurname, User.Username, User.UserExtraInfo,
    User.Email, User.Allergen, User.FF, Ingredient.Item, CookingStyle.Item, Category.Item {}
    public interface RequestInt extends Ingredient.Item, CookingStyle.Item, Category.Item {}
    public interface UsersFF extends User.NameSurname, User.Username, User.IDUser {}

    @Autowired
    private UsersService usersService;
    @Autowired
    private UserSession userSession;
    @Autowired
    private UsersRepository usersRepository;

    // SHOW ANOTHER USER PROFILE
    @JsonView(UsersRestController.AnotherUserProfile.class)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws IOException {
      if (userSession.isLoggedUser()){
        User actual = usersService.getActualUser(id).get();
        if (id != null) {
          return new ResponseEntity<>(actual, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }

    // LIST OF THE USER'S FOLLOWERS 
    @JsonView(UsersRestController.UsersFF.class)
    @GetMapping("/{id}/followers")
    public ResponseEntity<List<User>> getUserFollowers(@PathVariable Long id) throws IOException {
      if (userSession.isLoggedUser()){
        User actual = usersService.getActualUser(id).get();
        List<User> followers = usersService.getFollowersUsers(actual);
        if (id != null) {
          return new ResponseEntity<>(followers, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }

    // LIST OF THE USER'S FOLLOWING
    @JsonView(UsersRestController.UsersFF.class)
    @GetMapping("/{id}/following")
    public ResponseEntity<List<User>> getUserFollowing(@PathVariable Long id) throws IOException {
      if (userSession.isLoggedUser()){
        User actual = usersService.getActualUser(id).get();
        List<User> following = usersService.getFollowingUsers(actual);
        if (id != null) {
          return new ResponseEntity<>(following, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }

    // LIST OF THE USER'S FOLLOWERS AFTER PRESS FOLLOW ACTION
    @JsonView(UsersRestController.UsersFF.class)
    @PutMapping("/{id}/followAction")
    public ResponseEntity<List<User>> followAction(@PathVariable Long id) throws IOException {
      if (userSession.isLoggedUser()){
        if (id != null) {
          return new ResponseEntity<>(usersService.pressFollow(id), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }

    // LIST OF THE USER'S FOLLOWERS AFTER PRESS UNFOLLOW ACTION
    @JsonView(UsersRestController.UsersFF.class)
    @PutMapping("/{id}/unfollowAction")
    public ResponseEntity<List<User>> unfollowAction(@PathVariable Long id) throws IOException {
      if (userSession.isLoggedUser()){
        if (id != null) {
          return new ResponseEntity<>(usersService.pressUnfollow(id), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }
    
    //SHOW THE IMAGE OF ANOTHER USER
    @GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) throws IOException {
        if(userSession.isLoggedUser()){
          User user = usersRepository.findById(id).get();
          if(user.getImage().length > 0){
            byte[] image = user.getImage();
            return new ResponseEntity<>(image, HttpStatus.OK);
          }else{
            File file = new File("temp/avatars/image-"+user.getId()+".jpg");
            return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
          }
        }else{
          return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
    }
    
}