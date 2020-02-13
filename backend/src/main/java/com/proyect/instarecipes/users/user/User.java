package com.proyect.instarecipes.users.user;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_ID", nullable = false)
	private long id;
	
	@Column(name = "Username", nullable = false, unique = true, length = 24)
	private String username;
	@Column(name = "Email", nullable = false, unique = true, length = 24)
	private String email;
	@Column(name = "Password", nullable = false, length = 36)
	private String password;
	@Column(name = "Role", nullable = false, length = 5)
	private String role;				//"user","admin","annon"
	@Column(name = "Name", nullable = false, length = 24)
	private String name;
	@Column(name = "Surname", nullable = true, length = 24)
	private String surname;
	// private Image background;
	// private Image avatar;
	@Column(name = "Allergens", nullable = true, length = 24)
	private String allergens;
	
	@Column(name = "Followers", nullable = true, length = 24)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_follow_user",
        joinColumns = @JoinColumn(name = "followed_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id"))
	private Set<User> followers; //Set is like a collection of an objets that the items couldn't be repeated
	@Column(name = "Following", nullable = true, length = 24)
	@ManyToMany(mappedBy = "followers")
	private Set<User> following;
	
	public User() {
	}
	
	public User(long id, String username, String email, String password, String role, String name, String surname,
			String allergens, Set<User> followers, Set<User> following) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.name = name;
		this.surname = surname;
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

	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	
	public String isRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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

	// @Column(name = "background")
	// public Image getBackground() {
	// 	return background;
	// }
	// public void setBackground(Image background) {
	// 	this.background = background;
	// }

	// @Column(name = "avatar")
	// public Image getAvatar() {
	// 	return avatar;
	// }
	// public void setAvatar(Image avatar) {
	// 	this.avatar = avatar;
	// }

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
	}

	public Set<User> getFollowing() {
		return following;
	} 
	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public String getRole() { 
		return role;
	}
	
	/**				FOLLOWER / FOLLOWING METHODS				*/
	public void addFollower(User follower) {
        followers.add(follower); //he followed me, so i increment my followers list
        follower.following.add(this); //he followed me, so following list of him increments
	}

	public void addFollowing(User followed) {
        followed.addFollower(this);//i follow him, so i call addFollower() where im the parameter
    }
	/**				_____________________________				*/
}