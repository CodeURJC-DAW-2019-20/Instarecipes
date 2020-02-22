package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.CookingStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CookingStylesRepository extends JpaRepository<CookingStyle, Long> {

}