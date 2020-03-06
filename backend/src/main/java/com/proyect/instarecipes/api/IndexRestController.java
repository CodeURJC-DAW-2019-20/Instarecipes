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
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RecipesRepository;

@RestController
@RequestMapping("/api/")
public class IndexRestController{
	public interface Main extends User.Username, Recipe.RecipeBasic, Recipe.RecipePlus{}

    // private Map<Long, Item> items = new ConcurrentHashMap<>();
    @Autowired
    private RecipesRepository recipesRepository;

	// private AtomicLong lastId = new AtomicLong();

    // @JsonView(MainApiRest.Main.class)
	// @GetMapping("/")
	// public Collection<Recipe> recipes() {
	// 	return recipesRepository.findAll();
	// }

	// @PostMapping("/")
	// @ResponseStatus(HttpStatus.CREATED)
	// public Item nuevoItem(@RequestBody Item item) {

	// 	long id = lastId.incrementAndGet();
	// 	item.setId(id);
	// 	items.put(id, item);

	// 	return item;
	// }

	// @PutMapping("/{id}")
	// public ResponseEntity<Item> actulizaItem(@PathVariable long id, @RequestBody Item itemActualizado) {

	// 	Item item = items.get(id);

	// 	if (item != null) {

	// 		itemActualizado.setId(id);
	// 		items.put(id, itemActualizado);

	// 		return new ResponseEntity<>(itemActualizado, HttpStatus.OK);
	// 	} else {
	// 		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// 	}
	// }

    // @JsonView(IndexRestController.Main.class)
	// @GetMapping("/")
	// public ResponseEntity<Recipe> getItem(@RequestParam Long id) {

	// 	Optional<Recipe> recipe = recipesRepository.findById(id);

	// 	if (recipe.get() != null) {
	// 		return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
	// 	} else {
	// 		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// 	}
	// }

	// @DeleteMapping("/{id}")
	// public ResponseEntity<Recipe> borraItem(@PathVariable long id) {

	// 	Optional<Recipe> recipe = recipesRepository.findById(id);

	// 	if (recipe.get() != null) {
	// 		return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
	// 	} else {
	// 		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	// 	}
	// }

}