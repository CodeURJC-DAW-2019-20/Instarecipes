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

import com.proyect.instarecipes.models.CookingStyle;
import com.proyect.instarecipes.repositories.CookingStylesRepository;

@RestController
@RequestMapping("/api/cookingStyle")
public class CookingStyleRestController {

    @Autowired
    private CookingStylesRepository cookingStylesRepository;

	@GetMapping("/")
	public Collection<CookingStyle> cookingstyles() {
		return cookingStylesRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CookingStyle> getItem(@PathVariable long id) {

		Optional<CookingStyle> cookingStyle = cookingStylesRepository.findById(id);

		if (cookingStyle.get() != null) {
			return new ResponseEntity<>(cookingStyle.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}