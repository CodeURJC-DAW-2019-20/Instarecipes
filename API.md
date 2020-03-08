# Api Documentation

### Table of contents
* [Introduction](#intro)  
* [About our API](#about)  
* [How to use it and type of requests available](#useandrequests)  
* [Requests](#requests)  
  * [Authentication](#authentication)  
  	* [Login](#login)
	* [Logout](#logout) 
  * [By registered users](#registeredreq) 
  * [By anonymous users](#anonymousreq)  
  * [By administrator user](#adminreq)  
  * [Recipes requests](#recipesreq)  
 

## Introduction <a name="intro"></a>

## About our API <a name="about"></a>
All you can find in our API Rest is information about instarecipes, our recipe's website. You can make consults about users, ingredients, recipes, the ranking... 

Keep reading to know how!

## How to use it and type of requests available. <a name="useandrequests"></a>
  * First, you need to download [Postman](https://www.getpostman.com/).  
  * You only can send GET, POST and PUT requests.    
  * All API queries are preceded by **/api**.  

## Our API requests <a name="requests"></a>

  ## Authentication <a name="authentication"></a> 
   #### login : <a name="login"></a>
   > Allows a user to log in.
   
   * ##### URL:
        ```
        </login >  
        ```
   * ##### Method:  
         `GET`
   
   * ##### Success Response:
         ```
          {
             "username": "pepegrillo",
             "email": "pepe@grillo.com",
             "roles": [
                 "ROLE_USER"
             ],
             "name": "Pepe",
             "surname": "Grillo",
             "background": true,
             "avatar": true,
             "allergens": "Nuts",
             "followingNum": 5,
             "followersNum": 7,
             "info": "Hello World!! I hope you enjoy my recipes!"
           }
         ```
  
  * ##### Error response:

	**Code**: 401 UNAUTHORIZED


   #### logout : <a name="logout"></a>
   > Allows a user to logout.
   
   * ##### URL:
        ```
        </logout >  
        ```
   * ##### Method:  
         `GET`
   
   * ##### Success Response:
         ```
         true
         ```
  
  * ##### Error response:

	**Code**: 401 UNAUTHORIZED  
	
   ## Registered users <a name="registeredreq"></a>
The following queries will be preceded by /user or /profile. 
  
### Obtain user data
Resource to show all users with their data.
