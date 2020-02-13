package com.proyect.instarecipes.users;

import com.proyect.instarecipes.users.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
    // List<User> findByLastName(String lastName);
    // List<User> findByFirstName(String firstName);
}