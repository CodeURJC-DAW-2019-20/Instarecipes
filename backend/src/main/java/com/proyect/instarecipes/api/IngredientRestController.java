package com.proyect.instarecipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Optional;

import com.proyect.instarecipes.models.Ingredient;
import com.proyect.instarecipes.repositories.IngredientsRepository;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientRestController{

    @Autowired
    private IngredientsRepository ingredientsRepository;

	@GetMapping("/")
	public Collection<Ingredient> recipes() {
		return ingredientsRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ingredient> getItem(@PathVariable long id) {

		Optional<Ingredient> ingredient = ingredientsRepository.findById(id);

		if (ingredient.get() != null) {
			return new ResponseEntity<>(ingredient.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}