package com.proyect.instarecipes.repositories;

import java.util.List;

import com.proyect.instarecipes.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipe, Long> {
    // List<User> findByLastName(String lastName);
    // List<User> findByFirstName(String firstName);
    List<Recipe> findByUsernameId(Long username);
}