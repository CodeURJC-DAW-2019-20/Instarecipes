package com.proyect.instarecipes.repositories;

import com.proyect.instarecipes.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage users in database.
 * 
 * NOTE: This interface is intended to be extended by developer adding new
 * methods. Current method can not be removed because it is used in
 * authentication procedures.
 */
public interface UsersRepository extends JpaRepository<User, Long> {

	User findByUsername(String name);
	User findByEmail(String name);

}