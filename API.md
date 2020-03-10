# Api Documentation

### Table of contents
* [Introduction](#intro)  
* [About our API](#about)  
* [How to use it and type of requests available](#useandrequests)  
* [Requests](#requests)  
  * [Authentication](#authentication)  
  	* [Login](#login)
    * [Login as admin](#loginadmin)
	* [Logout](#logout) 
  * [Trending](#trending)  
    * [For registered users](#regtrending)
  	* [For anonymous users](#anontrending)
  * [Index page](#indexpage)
    * [For registered users](#registeredreq)
        * [Posting a recipe with images](#postrecipe)
        * [Recent publications](#recentpubslog)
    * [For anonymous users](#anonymousreq)
        * [Recent publications](#recentpubs)
  * [Profile](#profile)
    * [By normal users](#normalusers)
        * [Profile](#profile)
    * [By admin](#adminuser)
        * [Accept or denegate a request](#adminrequest)
        * [Request User List](#userslist)
        * [Request items list](#listitems)
        * [Profile](#adminprofile)
    * [By both](#bothusers)
        * [Update profile](#updateprofile)
        * [Request an item](#requestitem)
        * [Profile image](#profileimage)
  * [Ranking](#rankingreq) 
  * [Recipes](#recipesreq) 
  * [Like a comment](#likecomment)
  * [Unlike a comment](#unlikecomment)
  * [Like a recipe](#likerecipe)
  * [Unike a recipe](#unlikerecipe)
  * [Search a recipe by id](#recipeid)
  * [Steps from a recipe](#recipesteps)
  * [Steps from a recipe](#recipecomments)
  * [Post a comment](#postcomments)
  * [Filtered search](#filteredsearch)
  * [List of every recipe](#recipeslist)
  * [Sign up](#signup)
  * [More information about the users](#moreinfo)


## Introduction <a name="intro"></a>

## About our API <a name="about"></a>
All you can find in our API Rest is information about instarecipes, our recipe's website. You can make consults about users, ingredients, recipes, the ranking... 

Keep reading to know how!

## How to use it and type of requests available. <a name="useandrequests"></a>
  * First, you need to download [Postman](https://www.getpostman.com/).  
  * You only can send GET, POST and PUT requests.    
  * All API queries are preceded by **/api**.  

**IMPORTANT**  

Our API works with Basic Auth authorization, so if you want to login as admin or normal user go to authorization.   
The credentials are:    
- **Admin**:  
	> Username: admin  
	> Password: adminpass  
- **Normal user**:  
	> Username: pepegrillo  
	> Password: pass  

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


#### login as admin: <a name="loginadmin"></a>
   > Allows the admin to log in.
   
* ##### URL:
        
		 </login >  
        
* ##### Method:  
         `GET`
   
* ##### Success Response:
         
        {
            "username": "admin",
            "email": "hola@adios.com",
            "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
        ],
            "name": "Hamsa",
            "surname": "Jefe",
            "background": true,
            "avatar": true,
            "allergens": "cerdo",
            "followingNum": 5,
            "followersNum": 3,
            "info": "Hi people! Don't mess with me. I'm the boss hehe."
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
	

## Index page <a name="indexpage"></a>
The following queries will be preceded by /api

### For registered users <a name="registeredreq"></a>

### Post a recipe <a name="postrecipe"></a>
> Resource to post a recipe with **images**.

 * ##### URL: 

        </index >  
	

* ##### Method: 
         `POST`
   

* ##### Success response: 
	```
        {
        "user": {
            "username": "pepegrillo",
            "avatar": true
        },
        "title": "Vinagreta",
        "description": "This is the fking vinagreta",
        "duration": "44 min.",
        "difficulty": "izi",
        "firstStep": "Hola cabrones de micaelos",
        "allergen": "Nuts",
        "withImage": [
            "0",
            "0"
        ],
        "steps": [
            "Esto es el paso 2",
            "Esto es el paso 3"
        ],
        "ingredients": [
            "Potatoes"
        ],
        "categories": [
            "Dinner"
        ],
        "cookingStyles": [
            "Mediterranean"
        ]
        }
	```


* ##### Error response: 

	**Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

### Obtain the recent users publications <a name="recentpubslog"></a>

* ##### URL:

        </index >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
    ```
      [
      	  {
		    "username": {
		    "username": "pepegrillo",
		    "avatar": true
		},
		"title": "Vinagreta",
		"description": "This is the fking vinagreta",
		"likes": 0,
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
		    "username": "admin",
		    "avatar": true
		},
		"title": "Cheddar Cheese Sauce",
		"description": "Everyone loves cheese sauce over veggies, or for dipping. But of course there are all the pitfalls of eating cow dairy products. Here is a raw, live, vegan alternative that really stands up for applause!",
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
		"description": "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese.. And don't worry, It won't taste like vodka!",
		"likes": 2,
		"n_comments": 0,
		"image": true
	    }
	 ]
    ```
    
 * ##### Error response:

	**Code**: 401 NOT_FOUND 


### For anonymous users <a name="anonymousreq"></a>

### Obtain the recent users publications <a name="recentpubs"></a>

* ##### URL:

        </ >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
    ```
	[
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
		"description": "This salad features ripe avocado slices covered in a fresh lime dressing, topped generously with a contrasting crisp-and-crunchy blend of chopped radish, onion, peppers, and herbs.",
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
		"description": "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese.. And don't worry, It won't taste like vodka!",
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
		"description": "Everyone loves cheese sauce over veggies, or for dipping. But of course there are all the pitfalls of eating cow dairy products. Here is a raw, live, vegan alternative that really stands up for applause!",
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
		"description": "You are making hummus or some other dish with chickpeas and you are just wasting the chickpea water? How dare you! Didn't you know it can form the basis of some the most delicious, light, and foamy vegan ice creams and mousses?",
		"likes": 9,
		"n_comments": 0,
		"image": true
	    },
	    {
		"username": {
		    "username": "pepegrillo",
		    "avatar": true
		},
		"title": "Vinagreta",
		"description": "This is the fking vinagreta",
		"likes": 0,
		"n_comments": 0,
		"image": true
	    }
	]
    ```
    
 * ##### Error response:

	**Code**: 401 NOT_FOUND 

## Profile page <a name="profile"></a>
The following queries will be preceded by /api/profile

### For normal users <a name="normalusers"></a>

### View profile <a name="profileusers"></a> 
> Resource to show the logged user with his data.

* ##### URL:
        
        </ >  
        
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
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

### For admin user <a name="adminuser"></a>

### Accept or denegate a request <a name="adminrequest"></a> 
> Resource to accept or denegate a request.

* ##### URL:
        
        </admin/request >  

* ##### Method:  
         `POST`
    
* ##### Success Response:
     ```     
    [
        {
            "username": {
                "username": "trevrap",
                "name": "Trevod",
                "surname": "Rap",
                "avatar": true
            },
            "typeOfRequest": "Ingredient",
            "ingredientContent": "Potatoes",
            "cookingStyleContent": "",
            "categoryContent": "",
            "itemExists": true
        }
    ]
     ```   

* ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if request not founded 
	
	**Code**: LOCKED
	> if the user is not the admin

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user


### Request User List <a name="userslist"></a> 
> Resource to show the info about every user.

* ##### URL:
        
        </proadmin/users >  
        
* ##### Method:  
         `GET`

 * ##### Success Response:
    ```       
	[
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
	    },
	    {
		"username": "admin",
		"email": "hola@adios.com",
		"roles": [
		    "ROLE_USER",
		    "ROLE_ADMIN"
		],
		"name": "Hamsa",
		"surname": "Jefe",
		"background": true,
		"avatar": true,
		"allergens": "cerdo",
		"followingNum": 5,
		"followersNum": 3,
		"info": "Hi people! Don't mess with me. I'm the boss hehe."
	    },
	    {
		"username": "trevrap",
		"email": "trevodrap@hotmail.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "Trevod",
		"surname": "Rap",
		"background": true,
		"avatar": true,
		"allergens": "Peanuts",
		"followingNum": 5,
		"followersNum": 3,
		"info": "I'm a big fan of desserts, follow me and you will see it!"
	    },
	    {
		"username": "anuelbb",
		"email": "anuelAA@gmail.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "Anuel",
		"surname": "AA",
		"background": true,
		"avatar": true,
		"allergens": "Mustard",
		"followingNum": 5,
		"followersNum": 3,
		"info": "Mera woooo"
	    },
	    {
		"username": "pepitoram",
		"email": "pepito@gmail.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "Pedro",
		"surname": "Ramírez",
		"background": true,
		"avatar": true,
		"allergens": "Soy",
		"followingNum": 6,
		"followersNum": 3,
		"info": "This isn't real"
	    },
	    {
		"username": "manusav96",
		"email": "manu@gmail.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "Manuel",
		"surname": "Savater",
		"background": true,
		"avatar": true,
		"allergens": "Milk",
		"followingNum": 5,
		"followersNum": 5,
		"info": "Konichiwa people! I eat sushi every single day."
	    },
	    {
		"username": "oceloteLVP",
		"email": "ocelote@dios.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "Spanish",
		"surname": "Rocket",
		"background": true,
		"avatar": true,
		"allergens": null,
		"followingNum": 1,
		"followersNum": 3,
		"info": "Hi mum i'm famous"
	    },
	    {
		"username": "heisenberg",
		"email": "badbunny@gmail.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "Benito",
		"surname": "Boss",
		"background": true,
		"avatar": true,
		"allergens": "Wheat",
		"followingNum": 2,
		"followersNum": 3,
		"info": "#YHLQMDLG"
	    },
	    {
		"username": "user8",
		"email": "ozuna@hotmail.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "El Negrito",
		"surname": "Ojos Claros",
		"background": true,
		"avatar": true,
		"allergens": "Peanuts",
		"followingNum": 1,
		"followersNum": 3,
		"info": "Woooo"
	    },
	    {
		"username": "rodriram",
		"email": "s1mple@gmail.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "Rodrigo",
		"surname": "Ramírez",
		"background": true,
		"avatar": true,
		"allergens": null,
		"followingNum": 1,
		"followersNum": 3,
		"info": "wtf is this user"
	    },
	    {
		"username": "ifelse",
		"email": "electronic@grillo.com",
		"roles": [
		    "ROLE_USER"
		],
		"name": "Pepe",
		"surname": "Grillo",
		"background": false,
		"avatar": true,
		"allergens": "Eggs",
		"followingNum": 0,
		"followersNum": 0,
		"info": "Python>Java"
	    }
	]
    ```  

* ##### Error response:

	**Code**: LOCKED
	> if the user is not the admin

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user


### Request items list <a name="listitems"></a> 
> Resource to show the total of requests.

* ##### URL:
        
        </admin/request >  
  
        
* ##### Method:  
         `POST`

 * ##### Success Response:
    ```       


    ``` 

* ##### Error response:

	**Code**: LOCKED
	> if the user is not the admin

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user


### Admin profile <a name="adminprofile"></a> 
> Resource to view the admin profile.

* ##### URL:
        
        </admin >  
  
        
* ##### Method:  
         `GET`

 * ##### Success Response:
    ```       
    {
        "username": "admin",
        "email": "hola@adios.com",
        "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
        ],
        "name": "Hamsa",
        "surname": "Jefe",
        "background": true,
        "avatar": true,
        "allergens": "cerdo",
        "followingNum": 5,
        "followersNum": 3,
        "info": "Hi people! Don't mess with me. I'm the boss hehe."
    }

    ``` 

* ##### Error response:

	**Code**: LOCKED
	> if the user is not the admin

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user


### For both users <a name="bothusers"></a>

### Update profile <a name="updateprofile"></a>
> Resource to update the profile.

* ##### URL:
        
        </update >  
  
        
* ##### Method:  
         `PUT`

 * ##### Success Response:
         
         true??
  
* ##### Error response:

    **Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

### Send item request <a name="requestitem"></a>
> Resource to send a request item.

* ##### URL:
        
        </senditemrequest >  
        
* ##### Method:  
         `POST`

 * ##### Success Response:
         
         true??
         
* ##### Error response:

	**Code**: 401 NOT_FOUND 

### Profile image. <a name="profileimage"></a>
> Resource to view the profile image.

 * ##### URL: 
 
        </ >  

* ##### Method: 		
        `GET`
   
* ##### Success response: 


* ##### Error response:




## Ranking <a name="rankingreq"></a>
This query will be preceded by /api/ranking

 * ##### URL: 
 
        </ >  

* ##### Method: 		
        `GET`
   
* ##### Success response: 
    ```
    [
        [
            "pepegrillo",
            22
        ],
        [
            "trevrap",
            9
        ],
        [
            "admin",
            6
        ],
        [
            "manusav96",
            3
        ]
    ]
    ```

* ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if there is no ranking available
	

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

### Make a request to the admin
> Resource to show another user with his data.

* ##### URL:

        </users/{id} >  
	
* ##### Method:  
         `POST`
   
* ##### Success Response:
         
         true
         
* ##### Error response:

	**Code**: 401 NOT_FOUND 
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

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

### Users followers 
* ##### URL:

        </users/11/following >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
	```
     {
        "id": ,
        "username": "",
        "name": "",
        "surname": "",
        "avatar": 
    },
    {
        "id": ,
        "username": "",
        "name": "",
        "surname": "",
        "avatar": 
    },
    {
        "id": ,
        "username": "",
        "name": "",
        "surname": "",
        "avatar": 
    },
    {
        "id": ,
        "username": "",
        "name": "",
        "surname": "",
        "avatar": 
    },
    {
        "id": ,
        "username": "",
        "name": " ",
        "surname": " ",
        "avatar": 
    }
	```
	
* ##### Error response:  

	**Code**: 401 NOT_FOUND    
	> if user not founded 
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED  
	> if the user that makes the search is not logged

## Recipes  <a name="recipesreq"></a>
The following queries will be preceded by /api/recipes. 

### Like a comment <a name="likecomment"></a>

* ##### URL:

        </{id_recipe}/comments/{id_comment}/PressLike >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
	```
    {
        "userComment": {
            "username": "trevrap",
            "name": "Trevod",
            "surname": "Rap",
            "avatar": true
        },
        "content": "Cool",
        "hasSubcomments": false,
        "isSubcomment": false,
        "subComments": [],
        "likes": 3,
        "liked": true
    }
    
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the comment doesn't exist

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user


### Dislike a comment <a name="unlikecomment"></a>

* ##### URL:

        </{id_recipe}/comments/{id_comment}/UnpressLike >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
	```
    {
        "userComment": {
            "username": "trevrap",
            "name": "Trevod",
            "surname": "Rap",
            "avatar": true
        },
        "content": "Cool",
        "hasSubcomments": false,
        "isSubcomment": false,
        "subComments": [],
        "likes": 2,
        "liked": true
    }
    
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the comment doesn't exist

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user



### Like a recipe <a name="likerecipe"></a>

* ##### URL:

        </{id_recipe}/recipePressLike >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
	```
    {
        "username": {
            "username": "pepegrillo",
            "name": "Pepe",
            "surname": "Grillo",
            "avatar": true
        },
        "title": "Homemade Pizza!",
        "description": "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!",
        "likes": 9,
        "n_comments": 10,
        "ingredients": [
            {
                "ingredient": "Chicken"
            },
            {
                "ingredient": "Garlic"
            },
            {
                "ingredient": "Potatoes"
            }
        ],
        "categories": [
            {
                "category": "Main"
            },
            {
                "category": "Dinner"
            }
        ],
        "duration": "15 min.",
        "difficulty": "Hard",
        "image": true
    }
    
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the recipe doesn't exist

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user

### Dislike a recipe <a name="unlikerecipe"></a>

* ##### URL:

        </{id_recipe}/recipeUnpressLike >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
	```
    {
        "username": {
            "username": "pepegrillo",
            "name": "Pepe",
            "surname": "Grillo",
            "avatar": true
        },
        "title": "Homemade Pizza!",
        "description": "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!",
        "likes": 8,
        "n_comments": 10,
        "ingredients": [
            {
                "ingredient": "Potatoes"
            },
            {
                "ingredient": "Chicken"
            },
            {
                "ingredient": "Garlic"
            }
        ],
        "categories": [
            {
                "category": "Main"
            },
            {
                "category": "Dinner"
            }
        ],
        "duration": "15 min.",
        "difficulty": "Hard",
        "image": true
    }
    
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the recipe doesn't exist

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user

### Recipe by id <a name="recipeid"></a>

* ##### URL:

        </{id_recipe} >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
	```
    {
        "username": {
            "username": "manusav96",
            "name": "Manuel",
            "surname": "Savater",
            "avatar": true
        },
        "title": "Avocado Salad",
        "description": "This salad features ripe avocado slices covered in a fresh lime dressing, topped generously with a contrasting crisp-and-crunchy blend of chopped radish, onion, peppers, and herbs.",
        "likes": 1,
        "n_comments": 0,
        "ingredients": [
            {
                "ingredient": "Peppers"
            },
            {
                "ingredient": "Lettuce"
            },
            {
                "ingredient": "Garlic"
            },
            {
                "ingredient": "Radish"
            },
            {
                "ingredient": "Lemon"
            }
        ],
        "categories": [
            {
                "category": "Dinner"
            },
            {
                "category": "Main"
            },
            {
                "category": "Salad"
            },
            {
                "category": "Starters"
            },
            {
                "category": "Soups"
            }
        ],
        "duration": "15 min.",
        "difficulty": "Hard",
        "image": true
    }
        
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the recipe doesn't exist

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user

### Steps from a recipe <a name="recipesteps"></a>

* ##### URL:

        </{id_recipe}/steps >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
	```
    [
        {
            "number": 1,
            "content": "Preheat oven to 425 degrees F (220 degrees C). Lightly oil a large roasting pan.",
            "image": false
        },
        {
            "number": 2,
            "content": "Place chicken pieces in large bowl. Season with salt, oregano, pepper, rosemary, and cayenne pepper. Add fresh lemon juice, olive oil, and garlic. Place potatoes in bowl with the chicken; stir together until chicken and potatoes are evenly coated with marinade.",
            "image": false
        },
        {
            "number": 3,
            "content": "Transfer chicken pieces, skin side up, to prepared roasting pan, reserving marinade. Distribute potato pieces among chicken thighs. Drizzle with 2/3 cup chicken broth. Spoon remainder of marinade over chicken and potatoes.",
            "image": false
        }
    ]
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the steps doesn't exist

### Comments from a recipe <a name="recipecomments"></a>

* ##### URL:

        </{id_recipe}/comments/ >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
	```
    [
        {
            "userComment": {
                "username": "trevrap",
                "name": "Trevod",
                "surname": "Rap",
                "avatar": true
            },
            "content": "Cool",
            "hasSubcomments": false,
            "isSubcomment": false,
            "subComments": [],
            "likes": 2,
            "liked": false
        },
        {
            "userComment": {
                "username": "manusav96",
                "name": "Manuel",
                "surname": "Savater",
                "avatar": true
            },
            "content": "Do you eat cheesse?",
            "hasSubcomments": false,
            "isSubcomment": true,
            "subComments": [],
            "likes": 3,
            "liked": false
        },
        {
            "userComment": {
                "username": "pepegrillo",
                "name": "Pepe",
                "surname": "Grillo",
                "avatar": true
            },
            "content": "Yes i do",
            "hasSubcomments": false,
            "isSubcomment": true,
            "subComments": [],
            "likes": 0,
            "liked": false
        },
        {
            "userComment": {
                "username": "admin",
                "name": "Hamsa",
                "surname": "Jefe",
                "avatar": true
            },
            "content": "Lol what a conversation, so original ADMINLIKE",
            "hasSubcomments": false,
            "isSubcomment": true,
            "subComments": [],
            "likes": 4,
            "liked": false
        },
        {
            "userComment": {
                "username": "pepegrillo",
                "name": "Pepe",
                "surname": "Grillo",
                "avatar": true
            },
            "content": "Shut the fk up idiot",
            "hasSubcomments": false,
            "isSubcomment": true,
            "subComments": [],
            "likes": 0,
            "liked": false
        },
        {
            "userComment": {
                "username": "manusav96",
                "name": "Manuel",
                "surname": "Savater",
                "avatar": true
            },
            "content": "What happened in step 3?",
            "hasSubcomments": false,
            "isSubcomment": false,
            "subComments": [],
            "likes": 0,
            "liked": false
        },
        {
            "userComment": {
                "username": "admin",
                "name": "Hamsa",
                "surname": "Jefe",
                "avatar": true
            },
            "content": "Hello @trevodrap",
            "hasSubcomments": true,
            "isSubcomment": false,
            "subComments": [],
            "likes": 0,
            "liked": false
        },
        {
            "userComment": {
                "username": "pepegrillo",
                "name": "Pepe",
                "surname": "Grillo",
                "avatar": true
            },
            "content": "Hi m8 subscribe @trevodrap in my youtube channel",
            "hasSubcomments": false,
            "isSubcomment": true,
            "subComments": [],
            "likes": 0,
            "liked": false
        },
        {
            "userComment": {
                "username": "pepegrillo",
                "name": "Pepe",
                "surname": "Grillo",
                "avatar": true
            },
            "content": "This is awesome",
            "hasSubcomments": true,
            "isSubcomment": false,
            "subComments": [
                {
                    "userComment": {
                        "username": "manusav96",
                        "name": "Manuel",
                        "surname": "Savater",
                        "avatar": true
                    },
                    "content": "Do you eat cheesse?",
                    "hasSubcomments": false,
                    "isSubcomment": true,
                    "subComments": [],
                    "likes": 3,
                    "liked": false
                },
                {
                    "userComment": {
                        "username": "pepegrillo",
                        "name": "Pepe",
                        "surname": "Grillo",
                        "avatar": true
                    },
                    "content": "Shut the fk up idiot",
                    "hasSubcomments": false,
                    "isSubcomment": true,
                    "subComments": [],
                    "likes": 0,
                    "liked": false
                },
                {
                    "userComment": {
                        "username": "admin",
                        "name": "Hamsa",
                        "surname": "Jefe",
                        "avatar": true
                    },
                    "content": "Lol what a conversation, so original ADMINLIKE",
                    "hasSubcomments": false,
                    "isSubcomment": true,
                    "subComments": [],
                    "likes": 4,
                    "liked": false
                },
                {
                    "userComment": {
                        "username": "pepegrillo",
                        "name": "Pepe",
                        "surname": "Grillo",
                        "avatar": true
                    },
                    "content": "Yes i do",
                    "hasSubcomments": false,
                    "isSubcomment": true,
                    "subComments": [],
                    "likes": 0,
                    "liked": false
                }
            ],
            "likes": 2,
            "liked": false
        },
        {
            "userComment": {
                "username": "pepegrillo",
                "name": "Pepe",
                "surname": "Grillo",
                "avatar": true
            },
            "content": "Oh yes the comments are alright now ADMINLIKE",
            "hasSubcomments": true,
            "isSubcomment": false,
            "subComments": [
                {
                    "userComment": {
                        "username": "admin",
                        "name": "Hamsa",
                        "surname": "Jefe",
                        "avatar": true
                    },
                    "content": "Hello @trevodrap",
                    "hasSubcomments": true,
                    "isSubcomment": false,
                    "subComments": [],
                    "likes": 0,
                    "liked": false
                },
                {
                    "userComment": {
                        "username": "pepegrillo",
                        "name": "Pepe",
                        "surname": "Grillo",
                        "avatar": true
                    },
                    "content": "Hi m8 subscribe @trevodrap in my youtube channel",
                    "hasSubcomments": false,
                    "isSubcomment": true,
                    "subComments": [],
                    "likes": 0,
                    "liked": false
                }
            ],
            "likes": 4,
            "liked": false
        }
    ]
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if there are no comments


### Post a comment on a recipe by ID <a name="postcomments"></a>

* ##### URL:

        </{id_recipe}/comments/ >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
	```

    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the recipe doesn't exist

    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user


## Filtered search. <a name="filteredsearch"></a>
The following queries will be preceded by /api/search.

### Search a recipe 

* ##### URL:

        </navbar/recipes >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
	```
    [
        {
            "username": {
                "username": "pepegrillo",
                "name": "Pepe",
                "surname": "Grillo",
                "avatar": true
            },
            "title": "Homemade Pizza!",
            "description": "BEST pizza made with a garlic-herb crust, simple tomato sauce, tons of sauteed veggies, and parmesan cheese. Thin crust, tons of flavor, and ridiculously satisfying!",
            "cookingStyles": [
                {
                    "cookingStyle": "Mediterranean"
                },
                {
                    "cookingStyle": "American"
                },
                {
                    "cookingStyle": "Vegan"
                }
            ],
            "allergens": [
                {
                    "allergen": "Wheat"
                },
                {
                    "allergen": "Soy"
                }
            ],
            "ingredients": [
                {
                    "ingredient": "Chicken"
                },
                {
                    "ingredient": "Potatoes"
                },
                {
                    "ingredient": "Garlic"
                }
            ],
            "categories": [
                {
                    "category": "Dinner"
                },
                {
                    "category": "Main"
                }
            ],
            "image": true
        }
    ]
    ```
    
* ##### Error response:

	**Code**: NOT_FOUND
	> if the recipe doesn't exist

### Search an user. 

* ##### URL:

        </navbar/users >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
     ```
    [
        {
            "username": "pepegrillo",
            "name": "Pepe",
            "surname": "Grillo",
            "avatar": true
        }
    ]
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the recipe doesn't exist
    
    **Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if there is no logged user

### Filtered search recipe. 

* ##### URL:

        </filtered >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
     ```
    [
        {
            "username": {
                "username": "manusav96",
                "name": "Manuel",
                "surname": "Savater",
                "avatar": true
            },
            "title": "Avocado Salad",
            "description": "This salad features ripe avocado slices covered in a fresh lime dressing, topped generously with a contrasting crisp-and-crunchy blend of chopped radish, onion, peppers, and herbs.",
            "cookingStyles": [
                {
                    "cookingStyle": "Italian"
                }
            ],
            "allergens": [
                {
                    "allergen": "Nuts"
                }
            ],
            "ingredients": [
                {
                    "ingredient": "Lettuce"
                },
                {
                    "ingredient": "Garlic"
                },
                {
                    "ingredient": "Radish"
                },
                {
                    "ingredient": "Lemon"
                },
                {
                    "ingredient": "Peppers"
                }
            ],
            "categories": [
                {
                    "category": "Dinner"
                },
                {
                    "category": "Starters"
                },
                {
                    "category": "Salad"
                },
                {
                    "category": "Main"
                },
                {
                    "category": "Soups"
                }
            ],
            "image": true
        }
    ]   
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the recipe doesn't exist


## List of recipes. <a name="recipeslist"></a>
The following queries will be preceded by /api.

* ##### URL:

        </recipes >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
     ```
    [
        {
            "id": 7,
            "username": {
                "id": 1,
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
            "id": 6,
            "username": {
                "id": 2,
                "username": "admin",
                "avatar": true
            },
            "title": "Cheddar Cheese Sauce",
            "description": "Everyone loves cheese sauce over veggies, or for dipping. But of course there are all the pitfalls of eating cow dairy products. Here is a raw, live, vegan alternative that really stands up for applause!",
            "likes": 6,
            "n_comments": 0,
            "image": true
        },
        {
            "id": 5,
            "username": {
                "id": 6,
                "username": "manusav96",
                "avatar": true
            },
            "title": "Vodka Sauce Pasta (Pasta Party!)",
            "description": "Corn, Tomato, and Avocado Pasta Salad. Grab your favorite pasta, fresh cherry tomatoes, sweet corn, basil, cheddar cheese.. And don't worry, It won't taste like vodka!",
            "likes": 2,
            "n_comments": 0,
            "image": true
        },
        {
            "id": 4,
            "username": {
                "id": 6,
                "username": "manusav96",
                "avatar": true
            },
            "title": "Avocado Salad",
            "description": "This salad features ripe avocado slices covered in a fresh lime dressing, topped generously with a contrasting crisp-and-crunchy blend of chopped radish, onion, peppers, and herbs.",
            "likes": 1,
            "n_comments": 0,
            "image": true
        }
    ]
    ```
    
 * ##### Error response:

	**Code**: NOT_FOUND
	> if the recipe doesn't exist


## Sign up. <a name="signup"></a> 
The following queries will be preceded by /api.

* ##### URL:

        </signup >  
	
* ##### Method:  
         `POST`

* ##### Success Response: 
     ```
    [
        
    ]
    ```
    
* ##### Error response:

	**Code**: CONFLICT
	> if the user already exists.






## More information about the users. <a name="moreinfo"></a> 
The following queries will be preceded by /api/users.

### Users profiles.

* ##### URL:

        </{id} >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
     ```
    {
        "username": "manusav96",
        "email": "manu@gmail.com",
        "roles": [
            "ROLE_USER"
        ],
        "name": "Manuel",
        "surname": "Savater",
        "background": true,
        "avatar": true,
        "allergens": "Milk",
        "followingNum": 5,
        "followersNum": 5,
        "info": "Konichiwa people! I eat sushi every single day."
    }
    ```
    
 * ##### Error response:

    **Code**: 401 NOT_FOUND 
	> if the list is null
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged


### User following list.

* ##### URL:

        </{id}/following >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
     ```
    [
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
            "id": 10,
            "username": "user8",
            "name": "El Negrito",
            "surname": "Ojos Claros",
            "avatar": true
        }
    ]
    ```
    
* ##### Error response:

    **Code**: 401 NOT_FOUND 
	> if the list is null
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

### User followers list.

* ##### URL:

        </{id}/followers >  
	
* ##### Method:  
         `GET`

* ##### Success Response: 
     ```
    [
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
            "username": "heisenberg",
            "name": "Benito",
            "surname": "Boss",
            "avatar": true
        }
    ]
    ```
    
* ##### Error response:

    **Code**: 401 NOT_FOUND 
	> if the list is null
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

### Follow an user.

* ##### URL:

        </{id}/followAction >  
	
* ##### Method:  
         `PUT`

* ##### Success Response: 
     ```
    [
        {
            "id": 2,
            "username": "rodriram",
            "name": "Rodrigo",
            "surname": "Ramírez",
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
            "username": "anuelbb",
            "name": "Anuel",
            "surname": "AA",
            "avatar": true
        },
        {
            "id": 8,
            "username": "admin",
            "name": "Hamsa",
            "surname": "Jefe",
            "avatar": true
        },
        {
            "id": 9,
            "username": "heisenberg",
            "name": "Benito",
            "surname": "Boss",
            "avatar": true
        },
        {
            "id": 11,
            "username": "ifelse",
            "name": "Pepe",
            "surname": "Grillo",
            "avatar": true
        }
    ]
    ```
    
* ##### Error response:

    **Code**: 401 NOT_FOUND 
	> if the user doesn't exist.
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged


### Unfollow an user.

* ##### URL:

        </{id}/unfollowAction >  
	
* ##### Method:  
         `PUT`

* ##### Success Response: 
     ```
    [
        {
            "id": 2,
            "username": "rodriram",
            "name": "Rodrigo",
            "surname": "Ramírez",
            "avatar": true
        },
        {
            "id": 8,
            "username": "admin",
            "name": "Hamsa",
            "surname": "Jefe",
            "avatar": true
        },
        {
            "id": 6,
            "username": "anuelbb",
            "name": "Anuel",
            "surname": "AA",
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
            "id": 9,
            "username": "heisenberg",
            "name": "Benito",
            "surname": "Boss",
            "avatar": true
        }
    ]
    ```
    
* ##### Error response:

    **Code**: 401 NOT_FOUND 
	> if the user doesn't exist
	
	**Code**: 401 NETWORK_AUTHENTICATION_REQUIRED
	> if the user that makes the search is not logged

