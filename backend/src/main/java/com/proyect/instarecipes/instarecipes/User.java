package com.proyect.instarecipes.instarecipes;

import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class User{
	
	private AtomicInteger id;
	private String username;
	private String email;
	private String password;
	private String name;
	private String surname;
	private int N_recipes;
	private int N_follows;
	private int N_followers;
	private Image background;
	private Image avatar;
	private ArrayList<String> allergens;
	public AtomicInteger getId() {
		return id;
	}
	public void setId(AtomicInteger id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getN_recipes() {
		return N_recipes;
	}
	public void setN_recipes(int n_recipes) {
		N_recipes = n_recipes;
	}
	public int getN_follows() {
		return N_follows;
	}
	public void setN_follows(int n_follows) {
		N_follows = n_follows;
	}
	public int getN_followers() {
		return N_followers;
	}
	public void setN_followers(int n_followers) {
		N_followers = n_followers;
	}
	public Image getBackground() {
		return background;
	}
	public void setBackground(Image background) {
		this.background = background;
	}
	public Image getAvatar() {
		return avatar;
	}
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}
	public ArrayList<String> getAllergens() {
		return allergens;
	}
	public void setAllergens(ArrayList<String> allergens) {
		this.allergens = allergens;
	}
	
	
}