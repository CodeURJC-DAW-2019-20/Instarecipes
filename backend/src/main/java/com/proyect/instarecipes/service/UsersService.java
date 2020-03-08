package com.proyect.instarecipes.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.proyect.instarecipes.models.Recipe;
import com.proyect.instarecipes.models.Request;
import com.proyect.instarecipes.models.User;
import com.proyect.instarecipes.repositories.RequestsRepository;
import com.proyect.instarecipes.repositories.UsersRepository;
import com.proyect.instarecipes.security.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired 
    private UserSession userSession;


    public Optional<User> getActualUser(Long id) {
        return usersRepository.findById(id);
    }

    public ArrayList<Integer> getRecipesLikes(List<Recipe> recipes) {
        ArrayList<Integer> Laiks = new ArrayList<Integer>();
        for (int pubs = 0; pubs < recipes.size(); pubs++) {
            Laiks.add(recipes.get(pubs).getLikes());
        }
        return Laiks;
    }

    public ArrayList<String> getRecipesTitles(List<Recipe> recipes) {
        ArrayList<String> Titles = new ArrayList<String>();
        for (int pubs = 0; pubs < recipes.size(); pubs++) {
            Titles.add(recipes.get(pubs).getTitle());
        }
        return Titles;
    }

    public int getAllPubsLikes(List<Recipe> recipes) {
        int likes = 0;
        for (int pubs = 0; pubs < recipes.size(); pubs++) {
            likes = likes + recipes.get(pubs).getLikes();
        }
        return likes;
    }

    public List<User> getFollowingUsers(User u){
        return usersRepository.findFollowing(u.getUsername());
    }

    public List<User> getFollowersUsers(User u){
        return usersRepository.findFollowers(u.getUsername());
    }
    
    public boolean getIsFollowing(List<User> following, Long id){
        boolean isFollowing = false;
        for (User user : following) {
            if (user.getId() != id) {
                isFollowing = false;
            } else {
                isFollowing = true;
                break;
            }
        }
        return isFollowing;
    }
    
    public boolean getDisable(User u, Long id){
        boolean disable = false;
        if (u.getId() == id) {
            disable = false;
        } else {
            disable = true;
        }
        return disable;
    }

    public List<Request> getRequestList() {
        return requestsRepository.findAll();
    }

    public List<User> pressUnfollow(Long id){
        List<User> following = null;
        if (userSession.isLoggedUser()){
            User u1 = userSession.getLoggedUser();
            Optional<User> u2 = this.getActualUser(id);
            following= this.getFollowingUsers(u1);
            List<User> follower= this.getFollowersUsers(u2.get());
            Set<User> setFollowers=new HashSet<>();
            Set<User> setFollowing=new HashSet<>();
            //get the List of following of our user and the list of followers of the user that we are going to unfollow
            for(User user:following){
                if(user.getId()==id){ 
                }else{
                    setFollowing.add(user);  
                }
            }
            u1.setFollowing(setFollowing);
            usersRepository.followingNum(u1.getId(), setFollowing.size());
            // usersRepository.flush();
            for(User user:follower){
                if(user.getId()==u1.getId()){   
                }else{
                    setFollowers.add(user);
                }
            }
            u2.get().setFollowers(setFollowers);
            usersRepository.followerNum(u2.get().getId(), setFollowers.size());
            usersRepository.flush();

            following.clear();
            for(User user:setFollowing){
                following.add(user);
            }
        }
        return following;
    }

    public List<User> pressFollow(Long id){
        List<User> listfollowing = null;
        if (userSession.isLoggedUser()){
            User u1 = userSession.getLoggedUser();
            Optional<User> user1 = usersRepository.findById(u1.getId());
            Optional<User> u2 = usersRepository.findById(id);

            listfollowing = usersRepository.findFollowing(u1.getUsername());
            List<User> listfollowers = usersRepository.findFollowers(u2.get().getUsername());
            Set<User> setFollowing= new HashSet<>();
            Set<User> setFollower= new HashSet<>();
            //get the Set of following of our user and the Set of followers of the user that we are going to unfollow

            listfollowers.add(user1.get());
            for( User u : listfollowers){
                setFollower.add(u);
            }
            u2.get().setFollowers(setFollower);
            usersRepository.followerNum(u2.get().getId(), setFollower.size());

            listfollowing.add(u2.get()); 
            for( User u : listfollowing){
                setFollowing.add(u);
            }
            u1.setFollowing(setFollowing);
            usersRepository.followingNum(u1.getId(), setFollowing.size());   
            usersRepository.flush();   
        }
        return listfollowing;
    }
}
