package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {

	public interface Main extends User.UserSimple,     //username,email,password
								User.UserExtended{     //name,surname,background,avatar,allergens,followingNum, followerNum, info
	}

	@Autowired
	private UsersRepository usersRepository;

	@JsonView(Main.class)							
	@GetMapping("/")
 	public List<User> getListUsers() {
		return usersRepository.findAll();
	} 

	@JsonView(Main.class)
	@GetMapping("/{id}")
	public User getUser(@PathVariable long id) {
		return usersRepository.findById(id).get();
	}

	@JsonView(Main.class)
	@GetMapping("/username")
	public Collection<String> getUsernames() {
		return usersRepository.findAll().stream().map(b -> b.getUsername()).collect(Collectors.toList());
	}


	/* @JsonView(ProfileRestController.Main.class)
	@PostMapping("/{username}")
	public ResponseEntity<User> updateProfile(@RequestBody long id) {					//change name+surname+bio+allergens+avatar+background	
	} */
	
	/*   @JsonView(ProfileRestController.Main.class)
	  @GetMapping("/{id}")
	  public User actualizaItem(@PathVariable
	  long id, @RequestBody User user){
		Optional<User> us = usersRepository.findById(id);
		return us.get();
	  } */
	
	// @PostMapping("/")
	// @ResponseStatus(HttpStatus.CREATED)
	// public Item nuevoItem(@RequestBody Item item) {

	// long id = lastId.incrementAndGet();
	// item.setId(id);
	// items.put(id, item);

	// return item;
	// }

	// @PutMapping("/{id}")
	// public ResponseEntity<Item> actulizaItem(@PathVariable long id, @RequestBody
	// Item itemActualizado) {

	// Item item = items.get(id);

	// if (item != null) {

	// itemActualizado.setId(id);
	// items.put(id, itemActualizado);

	// return new ResponseEntity<>(itemActualizado, HttpStatus.OK);
	// } else {
	// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// }
	// }

	/*
	 * @GetMapping("/{id}") public ResponseEntity<User> getItem(@PathVariable long
	 * id) {
	 * 
	 * Optional<User> user = userRepository.findById(id);
	 * 
	 * if (recipe.get() != null) { return new ResponseEntity<>(recipe.get(),
	 * HttpStatus.OK); } else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
	 * }
	 */
	// @DeleteMapping("/{id}")
	// public ResponseEntity<Recipe> borraItem(@PathVariable long id) {

	// Optional<Recipe> recipe = recipesRepository.findById(id);

	// if (recipe.get() != null) {
	// return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
	// } else {
	// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// }
	// }

}