package com.proyect.instarecipes.repositories;

import java.util.Optional;

import com.proyect.instarecipes.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM Ingredient i WHERE i.ingredient = :ingredient")
    Optional<Ingredient> findByIngredient(String ingredient);
}