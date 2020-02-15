package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.User;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    // List<User> findByLastName(String lastName);
    // List<User> findByFirstName(String firstName);
    User findByName(String name);
    //User findByUsername(String username);
}