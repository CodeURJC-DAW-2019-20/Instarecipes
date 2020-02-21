package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
}