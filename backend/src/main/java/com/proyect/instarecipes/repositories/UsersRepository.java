package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
    // List<User> findByLastName(String lastName);
    // List<User> findByFirstName(String firstName);
}