package com.proyect.instarecipes.repositories;

import java.util.Set;

import com.proyect.instarecipes.models.CookingStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface CookingStylesRepository extends JpaRepository<CookingStyle, Long> {

    @Query("SELECT cS FROM CookingStyle cS WHERE cS.cookingStyle = :cookingStyle")
	Set<CookingStyle> findByName(String cookingStyle);

}