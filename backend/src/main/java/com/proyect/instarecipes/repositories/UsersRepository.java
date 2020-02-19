package com.proyect.instarecipes.repositories;

import java.util.List;

import com.proyect.instarecipes.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	@Query("SELECT followers FROM User u WHERE u.username = :name")
	List<User> findFollowers(String name);
	@Query("SELECT following FROM User u WHERE u.username = :name")
	List<User> findFollowing(String name);
}