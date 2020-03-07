package com.proyect.instarecipes.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User{

	public interface Username{}
	public interface NameSurname{}
	public interface UserExtraInfo{}
	public interface Email{}
	public interface Allergen{}
	public interface FF{}

	public interface UserRanking{}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonView(Username.class)
	@Column(nullable = false, unique = true)
	private String username;
	
	@JsonView(Email.class)
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@JsonView(UserExtraInfo.class)
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	
	@JsonView(NameSurname.class)
	@Column(nullable = false)
	private String name;

	@JsonView(NameSurname.class)
	@Column(nullable = false)
	private String surname;

	@JsonView(UserExtraInfo.class)
	private boolean background;

	@JsonView(Username.class)
	private boolean avatar;

	@JsonView(Allergen.class)
	private String allergens;

	@JsonView(FF.class)
	private int followingNum;

	@JsonView(FF.class)
	private int followersNum;

	@JsonView(UserExtraInfo.class)
	@Column(nullable = false)
	private String info;


	@ManyToMany(cascade = CascadeType.ALL)
	private Set<User> followers; //Set is like a collection of an objets that the items couldn't be repeated
	
	@ManyToMany(mappedBy = "followers")
	private Set<User> following;
	
	public User() {}

	public User(String username, String email, String password, String name, String surname, String info,
			String allergens, Set<User> followers, Set<User> following, String... roles) { //THIS ROLE PARAM NECESSARY HAS TO BE AT THE END OF THE COSTRUCTOR
		this.username = username;
		this.email = email;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.roles = new ArrayList<>(Arrays.asList(roles));
		this.name = name;
		this.surname = surname;
		this.info = info;
		this.allergens = allergens;
		this.followers = followers;
		this.following = following;
		
	}

	//this constructor will be called once the user logged in
    public User(String name) {
		//HashSet is like a map of an objets that the items couldn't be repeated
        this.name = name;
        this.followers = new HashSet<User>();
        this.following = new HashSet<User>();
    }

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	public String getAllergens() {
		return allergens;
	}
	public void setAllergens(String allergens) {
		this.allergens = allergens;
	}

	public Set<User> getFollowers() { 
		return followers;
	}
	public void setFollowers(Set<User> followers) {
		this.followers = followers;
		this.followersNum = this.followers.size();
	}

	public Set<User> getFollowing() {
		return following;
	} 
	public void setFollowing(Set<User> following1) {
		this.following = following1;
		this.followingNum = following.size();
	}

	public List<String> getRoles() { 
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	// public void setImage(boolean image){
    //     this.image=image;
    // }

	public String toString(){
        if(this.roles.contains("ROLE_ADMIN")){
            return "admin";
        }else{
            return "user";
        }
	}
	
	/**				FOLLOWER / FOLLOWING METHODS				*/

	/**				_____________________________				*/

	public boolean isBackground() {
		return background;
	}

	public void setBackground(boolean background) {
		this.background = background;
	}

	public boolean isAvatar() {
		return avatar;
	}

	public void setAvatar(boolean avatar) {
		this.avatar = avatar;
	}

	public int getFollowingNum() {	
		return followingNum;
	}

	public void setFollowingNum(int followingNum) {
		this.followingNum = followingNum;
	}

	public int getFollowersNum() {
		return followersNum;
	}

	public void setFollowersNum(int followersNum) {
		this.followersNum = followersNum;
	}

}