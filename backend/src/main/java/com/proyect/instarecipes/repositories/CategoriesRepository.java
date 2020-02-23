package com.proyect.instarecipes.repositories;

import java.util.Optional;

import com.proyect.instarecipes.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.category = :category")
    Optional<Category> findByCategory(String category);
}