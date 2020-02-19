package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepository extends JpaRepository<Request, Long> {
}