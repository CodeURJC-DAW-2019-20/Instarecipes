package com.proyect.instarecipes.models;

import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.*;

@Entity
public class User{
	@Id //PRIMARY KEY
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
	@ElementCollection(fetch=FetchType.EAGER)
    private List<String> role;			//"user","admin","annon"
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
	
	public User(long id, String username, String email, String password, String[] role, String name, String surname,
			String allergens, Set<User> followers, Set<User> following) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = new ArrayList<>(Arrays.asList(role));
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

	public List<String> getRole() { 
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public String toString(){
        if(this.role.contains("ROLE_ADMIN")){
            return "admin";
        }else{
            return "user";
        }
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