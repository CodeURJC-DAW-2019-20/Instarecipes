package com.proyect.instarecipes.repositories;

import java.util.Optional;

import com.proyect.instarecipes.models.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface AllergensRepository extends JpaRepository<Allergen, Long> {

    @Query("SELECT a FROM Allergen a WHERE a.allergen = :allergen")
    Optional<Allergen> findByCategory(String allergen);

}