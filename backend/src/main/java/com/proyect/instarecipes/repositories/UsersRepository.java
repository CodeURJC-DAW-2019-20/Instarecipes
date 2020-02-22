package com.proyect.instarecipes.repositories;

import java.util.List;

import com.proyect.instarecipes.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

	User findByUsername(String name);
	User findByEmail(String name);

	@Query("SELECT followers FROM User u WHERE u.username = :name")
	List<User> findFollowers(String name);
	@Query("SELECT following FROM User u WHERE u.username = :name")
	List<User> findFollowing(String name);
}