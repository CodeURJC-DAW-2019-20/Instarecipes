# Api Documentation

### Table of contents
* [Introduction](#intro)  
* [About our API](#about)  
* [How to use it and type of requests available](#useandrequests)  
* [Requests](#requests)  
  * [Authentication](#authentication)  
  	* [Login](#login)
	* [Logout](#logout) 
  * [Trending](#trending)
 	* [For registered users](#regtrending)
  	* [For anonymous users](#anontrending)
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
        
		 </login >  
        
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
       
        </logout >  
        
* ##### Method:  

         `GET`
   
* ##### Success Response:
         
         true
         
* ##### Error response:

	**Code**: 401 UNAUTHORIZED  
	
## Trending.  

**Trending for registered users** <a name="regtrending"></a>
> Resource to show the trending (advanced algorithm).
> *In this case, the user is allergic to soy*  

* ##### URL:

        </trending >  
	
* ##### Method:  
         `GET`
   
* ##### Success Response:
    ```
    {
        "username": {
            "username": "pepegrillo",
            "avatar": true
        },
        "title": "Baked Fish with Lemon Cream Sauce",
        "description": "Yup, just throw it all in one pan, bake it, and you end up with a tender juicy fish in a creamy lemon sauce.",
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
        "description": "You are making hummus or some other dish with chickpeas and you are just wasting the chickpea water? How dare you! Didn't you know it can form the basis of some the most delicious, light, and foamy vegan ice creams and mousses?",
        "likes": 9,
        "n_comments": 0,
        "image": true
    },
    {
        "username": {
            "username": "admin",
            "avatar": true
        },
        "title": "Cheddar Cheese Sauce",
        "description": "Everyone loves cheese sauce over veggies, or for dipping. But of course there are all the pitfalls of eating cow dairy products. Here is a raw, live, vegan alternative that really stands up for applause!",
        "likes": 6,
        "n_comments": 0,
        "image": true
    }  
    ``` 
* ##### Error response:

	**Code**: 401 NOT_FOUND 
	
**Trending for anonymous users** <a name="anontrending"></a>

* ##### URL:
        
        </trending/notLogged >  
        
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
	
## Registered users <a name="registeredreq"></a>
The following queries will be preceded by /user or /profile. 
  
### Obtain logged user data
> Resource to show the logged user with his data.

* ##### URL:
        
        </profile >  
        
* ##### Method:  
         `GET`

 * ##### Success Response:
         
         true
         
* ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

### Obtain the data of another user
> Resource to show another user with his data.
* ##### URL:

        </users/{id} >  
	
* ##### Method:  
         `GET`
   
* ##### Success Response:
         
         true
         
* ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

### Obtain the recent users publications

* ##### URL:

        </index >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
	```
    {
    "username": {
    	"username": "pepegrillo",
    	"avatar": true
    },
    "title": "Olives and Sun-Dried Tomato Couscous",
    "description": "A delicate, flavorful dish that will satisfy vegans and carnivores alike!.",
    "likes": 2,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
	    "username": "admin",
	    "avatar": true
    },
    "title": "Cheddar Cheese Sauce",
    "description": "Everyone loves cheese sauce over veggies, or for dipping. But of course there are all the pitfalls of eating cow 		dairy products. Here is a raw, live, vegan alternative that really stands up for applause!",
    "likes": 6,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
    	"username": "manusav96",
    	"avatar": true
    },
    "title": "Vodka Sauce Pasta (Pasta Party!)",
    "description": "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar 		cheese.. And don't worry, It won't taste like vodka!",
    "likes": 2,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
    	"username": "manusav96",
    	"avatar": true
    },
    "title": "Avocado Salad",
    "description": "This salad features ripe avocado slices covered in a fresh lime dressing, topped generously with a contrasting 		crisp-and-crunchy blend of chopped radish, onion, peppers, and herbs.",
    "likes": 1,
    "n_comments": 0,
    "image": true
    }
    ```
    
 * ##### Error response:

	**Code**: 401 NOT_FOUND 

### Search an user
> Resource to search an user.

* ##### URL:
        
        </search/navbar/users >  
        
* ##### Method:  
         `POST`

 * ##### Success Response:   
   ```      
    {
    	"username": "pepegrillo",
   	"name": "Pepe",
    	"surname": "Grillo",
    	"avatar": true
    }
   ```
         
* ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged


### Follow an user 
* ##### URL:

        </users/11/followAction >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
	```
     {
        "id": 1,
        "username": "pepegrillo",
        "name": "Pepe",
        "surname": "Grillo",
        "avatar": true
    },
    {
        "id": 2,
        "username": "heisenberg",
        "name": "Benito",
        "surname": "Boss",
        "avatar": true
    },
    {
        "id": 4,
        "username": "manusav96",
        "name": "Manuel",
        "surname": "Savater",
        "avatar": true
    },
    {
        "id": 6,
        "username": "admin",
        "name": "Hamsa",
        "surname": "Jefe",
        "avatar": true
    },
    {
        "id": 8,
        "username": "anuelbb",
        "name": "Anuel",
        "surname": "AA",
        "avatar": true
    },
    {
        "id": 10,
        "username": "rodriram",
        "name": "Rodrigo",
        "surname": "Ramírez",
        "avatar": true
    },
    {
        "id": 11,
        "username": "ifelse",
        "name": "Pepe",
        "surname": "Grillo",
        "avatar": true
    }
	```
* ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

	
### Unfollow an user 
* ##### URL:

        </users/11/followAction >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
	```
     {
        "id": 1,
        "username": "pepegrillo",
        "name": "Pepe",
        "surname": "Grillo",
        "avatar": true
    },
    {
        "id": 2,
        "username": "heisenberg",
        "name": "Benito",
        "surname": "Boss",
        "avatar": true
    },
    {
        "id": 4,
        "username": "manusav96",
        "name": "Manuel",
        "surname": "Savater",
        "avatar": true
    },
    {
        "id": 6,
        "username": "admin",
        "name": "Hamsa",
        "surname": "Jefe",
        "avatar": true
    },
    {
        "id": 8,
        "username": "anuelbb",
        "name": "Anuel",
        "surname": "AA",
        "avatar": true
    },
    {
        "id": 10,
        "username": "rodriram",
        "name": "Rodrigo",
        "surname": "Ramírez",
        "avatar": true
    }
	```
* ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

	
### Users followers 
* ##### URL:

        </users/11/following >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
	```
     {
        "id": 1,
        "username": "pepegrillo",
        "name": "Pepe",
        "surname": "Grillo",
        "avatar": true
    },
    {
        "id": 3,
        "username": "trevrap",
        "name": "Trevod",
        "surname": "Rap",
        "avatar": true
    },
    {
        "id": 5,
        "username": "oceloteLVP",
        "name": "Spanish",
        "surname": "Rocket",
        "avatar": true
    },
    {
        "id": 7,
        "username": "pepitoram",
        "name": "Pedro",
        "surname": "Ramírez",
        "avatar": true
    },
    {
        "id": 9,
        "username": "user8",
        "name": "El Negrito",
        "surname": "Ojos Claros",
        "avatar": true
    }
	```
	
* ##### Error response:  

	**Code**: 401 NOT_FOUND    
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED  
	> if the user that makes the search is not logged

## Anonymous users <a name="anonymousreq"></a>
The following queries will be preceded by /. 

### Obtain the recent users publications

* ##### URL:

        </ >  
	
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
    "description": "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin 		crust, tons of flavor, and ridiculously satisfying!",
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
    "description": "Yup, just throw it all in one pan, bake it, and you end up with a tender juicy fish in a creamy lemon sauce.",
    "likes": 9,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
    	"username": "pepegrillo",
    	"avatar": true
        },
    "title": "Thai Chicken Soup with Rice Noodles",
    "description": "Cozy, comforting, and fragrant, this flavor-packed Thai chicken noodle soup will warm you right up.",
    "likes": 2,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
    	"username": "manusav96",
    	"avatar": true
    },
    "title": "Avocado Salad",
    "description": "This salad features ripe avocado slices covered in a fresh lime dressing, topped generously with a contrasting 			crisp-and-crunchy blend of chopped radish, onion, peppers, and herbs.",
    "likes": 1,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
    	"username": "manusav96",
    	"avatar": true
    },
    "title": "Vodka Sauce Pasta (Pasta Party!)",
    "description": "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar 		cheese.. And don't worry, It won't taste like vodka!",
    "likes": 2,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
    	"username": "admin",
    	"avatar": true
    },
    "title": "Cheddar Cheese Sauce",
    "description": "Everyone loves cheese sauce over veggies, or for dipping. But of course there are all the pitfalls of eating cow 		dairy products. Here is a raw, live, vegan alternative that really stands up for applause!",
    "likes": 6,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
    	"username": "pepegrillo",
    	"avatar": true
    },
    "title": "Olives and Sun-Dried Tomato Couscous",
    "description": "A delicate, flavorful dish that will satisfy vegans and carnivores alike!.",
    "likes": 2,
    "n_comments": 0,
    "image": true
    },
    {
    "username": {
   	"username": "trevrap",
   	"avatar": true
    },
    "title": "Vegan Chocolate Ice Cream",
    "description": "You are making hummus or some other dish with chickpeas and you are just wasting the chickpea water? How dare you! 		Didn't you know it can form the basis of some the most delicious, light, and foamy vegan ice creams and mousses?",
    "likes": 9,
    "n_comments": 0,
    "image": true
    }
    ```
    
 * ##### Error response:

	**Code**: 401 NOT_FOUND 
