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

import com.proyect.instarecipes.models.Allergen;
import com.proyect.instarecipes.repositories.AllergensRepository;

@RestController
@RequestMapping("/api/allergen")
public class AllergenRestController{

    @Autowired
    private AllergensRepository allergensRepository;

	@GetMapping("/")
	public Collection<Allergen> recipes() {
		return allergensRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Allergen> getItem(@PathVariable long id) {

		Optional<Allergen> allergen = allergensRepository.findById(id);

		if (allergen.get() != null) {
			return new ResponseEntity<>(allergen.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}