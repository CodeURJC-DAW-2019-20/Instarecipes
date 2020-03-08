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
         
         true
         
  * ##### Error response:

	**Code**: 401 UNAUTHORIZED  
	
## Registered users <a name="registeredreq"></a>
The following queries will be preceded by /user or /profile. 
  
### Obtain logged user data
> Resource to show the logged user with his data.
* ##### URL:
        ```
        </users/{id} >  
        ```
### Obtain the data of another user
> Resource to show another user with his data.
   * ##### URL:
        ```
        </users/{id} >  
        ```
   * ##### Method:  
         `GET`
   
   * ##### Success Response:
         
         true
         
  * ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

## Obtain trending for registered users.
> Resource to show the trending (advanced algorithm).
* ##### URL:
        ```
        </trending >  
        ```
* ##### Method:  
         `GET`
   
   * ##### Success Response:
    ```
    {
	"username": {
	    "username": "pepegrillo",
	    "avatar": true
	},
	"title": "Homemade Pizza!",
	"description": "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!",
	"likes": 9,
	"n_comments": 10,
	"image": true
    },
    {
	"username": {
	    "username": "pepegrillo",
	    "avatar": true
	},
	"title": "Baked Fish with Lemon Cream Sauce",
	"description": "Yup, just throw it all in one pan, bake it, and you end up with a tender juicy fish in a creamy lemon 	                 sauce.",  
	"likes": 9,  
	"n_comments": 0,  
	"image": true  
    },  
    {  
	"username": {  
	    "username": "trevrap",  
	    "avatar": true  
	},  
	"title": "Vegan Chocolate Ice Cream",  
	"description": "You are making hummus or some other dish with chickpeas and you are just wasting the chickpea water? How 		 dare you! Didn't you know it can form the basis of some the most delicious, light, and foamy vegan ice creams and 	                 mousses?",  
	"likes": 9,  
	"n_comments": 0,  
	"image": true  
    }   
    ``` 
  * ##### Error response:

	**Code**: 401 NOT_FOUND 
	
