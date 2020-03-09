package com.proyect.instarecipes.api;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    private UserSession usersession;
    @Autowired
    private UsersRepository usersRepository;

    @JsonView(UsersRestController.AnotherUserProfile.class)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws IOException {
      if (usersession.isLoggedUser()){
        Optional<User> actual = usersService.getActualUser(id);
        if (id != null) {
          return new ResponseEntity<>(actual.get(), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }

  //   @GetMapping(value = "/{id}/image")
	// public ResponseEntity<byte[]> getProfileImage(@RequestParam("id") Long id) {
	// 	Optional<User> User = usersRepository.findById(id);
	// 	if (!User.isPresent()) {
  //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  //   }
  // }

    @JsonView(UsersRestController.UsersFF.class)
    @GetMapping("/{id}/followers")
    public ResponseEntity<List<User>> getUserFollowers(@PathVariable Long id) throws IOException {
      if (usersession.isLoggedUser()){
        Optional<User> actual = usersService.getActualUser(id);
        List<User> followers = usersService.getFollowersUsers(actual.get());
        if (id != null) {
          return new ResponseEntity<>(followers, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }

    @JsonView(UsersRestController.UsersFF.class)
    @GetMapping("/{id}/following")
    public ResponseEntity<List<User>> getUserFollowing(@PathVariable Long id) throws IOException {
      if (usersession.isLoggedUser()){
        Optional<User> actual = usersService.getActualUser(id);
        List<User> following = usersService.getFollowingUsers(actual.get());
        if (id != null) {
          return new ResponseEntity<>(following, HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }

    @JsonView(UsersRestController.UsersFF.class)
    @PutMapping("/{id}/followAction")
    public ResponseEntity<List<User>> followAction(@PathVariable Long id)throws IOException {
      if (usersession.isLoggedUser()){
        if (id != null) {
          return new ResponseEntity<>(usersService.pressFollow(id), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }

    @JsonView(UsersRestController.UsersFF.class)
    @PutMapping("/{id}/unfollowAction")
    public ResponseEntity<List<User>> unfollowAction(@PathVariable Long id) throws IOException {
      if (usersession.isLoggedUser()){
        if (id != null) {
          return new ResponseEntity<>(usersService.pressUnfollow(id), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
      }else {
        return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
      }
    }
    
	@GetMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
      Optional<User> User = usersRepository.findById(id);
      if (!User.isPresent()){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
      else{
        User profile = User.get();
        if(profile.getImage() != null){
          return new ResponseEntity<>(profile.getImage(), HttpStatus.OK);
        }else{
          return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
      }
  }

  @PostMapping(value = "/{id}/image",produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<byte[]> setProfileImage(@PathVariable Long id, @RequestBody MultipartFile image) throws IOException {
      Optional<User> User = usersRepository.findById(id);
      if (!User.isPresent()){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      else{
        User profile = User.get();
        User u = usersession.getLoggedUser();
        if(u.getId() == profile.getId()){
          profile.setAvatar(true);
          profile.setImage(image.getBytes());
          usersRepository.flush();
          return new ResponseEntity<>(profile.getImage(), HttpStatus.OK);
        }else{
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
      }
  }


}