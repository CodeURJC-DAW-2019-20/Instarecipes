package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
    // List<User> findByLastName(String lastName);
    // List<User> findByFirstName(String firstName);
}