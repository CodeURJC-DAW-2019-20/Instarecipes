package com.proyect.instarecipes.repositories;

import java.util.List;
import java.util.Set;

import com.proyect.instarecipes.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

	User findByUsername(String name);
	User findByEmail(String name);
	@Query("DELETE FROM User u  WHERE u.id= :id")
	void followers_delete(Long id);
	@Query("SELECT followers FROM User u WHERE u.username = :name")
	List<User> findFollowers(String name);
	@Query("SELECT following FROM User u WHERE u.username = :name")
	List<User> findFollowing(String name);
	@Query("SELECT following FROM User u WHERE u.username = :name")
	Set<User> findFollowingSet(String name);
	@Query("SELECT followers FROM User u WHERE u.username = :name")
	Set<User> findFollowersSet(String name);
	// @Modifying
	// @Query("UPDATE User u SET u.following = :following WHERE u.id = :id")
	// void following(Long id, User use);
}