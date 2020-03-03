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

import com.fasterxml.jackson.annotation.JsonView;
import com.proyect.instarecipes.models.Step;
import com.proyect.instarecipes.repositories.StepsRepository;

@RestController
@RequestMapping("/api/step")
public class StepRestController{
    interface StepInfo extends Step.Simple{}

    @Autowired
    private StepsRepository stepsRepository;

    @JsonView(StepRestController.StepInfo.class)
	@GetMapping("/")
	public Collection<Step> recipes() {
		return stepsRepository.findAll();
	}

    //THIS RETURNS ONLY THE ATTRIBUTTES THAT HAS BEEN ANOTATED WITH @JsonView IN THE ENTITY
    @JsonView(StepRestController.StepInfo.class)
	@GetMapping("/{id}")
	public ResponseEntity<Step> getItem(@PathVariable long id) {

		Optional<Step> step = stepsRepository.findById(id);

		if (step.get() != null) {
			return new ResponseEntity<>(step.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}