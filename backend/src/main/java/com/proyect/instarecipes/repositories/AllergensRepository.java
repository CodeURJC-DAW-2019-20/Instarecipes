package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergensRepository extends JpaRepository<Allergen, Long> {

}