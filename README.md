# Group 13 of DAW   ![Active](https://img.shields.io/badge/version-0.1-blue)
This is the developer project's repository, where you can found folders like **Code**'s folder, that contains all the stuff related to the application developing code, or the **Prototype**'s folder, where you will be able to see our first ideas and some sketches used to carry out the web application.

# Table of contents
**PHASE 1**.
* [Introduction](#phase1)  
  * [Members](#members)  
  * [Support links](#supplink)  
  * [What is our product about?](#intro)  
* [Funcionalities](#funcionalities)  
* [Entities](#entities)  
* [Types of users](#users)  
  * [Anonymous](#anonymousUser)  
  * [Registered](#registeredUser)  
  * [Aministrator](#adminUser)  
* [Advanced algorithm](#algorithm)
* [Navigation Diagrams](#navDiagram)

**PHASE 2** 
 * [Most important commits](#importantComm)  
 * [Most worked files](#workedFiles)    
 * [Instructions](#instructions)  
 * [New navigation diagrams](#navDiagrams2)
 * [Database diagrams](#dbDiagram)  
 * [Classes and templates diagrams](#CTdiagrams)  
  
# Page layout with HTML and CSS <a name="phase1"></a>

## Members :busts_in_silhouette: <a name="members"></a>
|                           |                                 |
|---------------------------|---------------------------------|
| Daniel Murillo Garzón     | d.murillo.2018@alumnos.urjc.es  |
| Hamsa Aldrobi Elharti     | h.aldrobi@alumnos.urjc.es       |
| Manuel León Briz          | m.leon.2016@alumnos.urjc.es     |
| José María Melero Gimenez | jm.melero.2016@alumnos.urjc.es  |
| Raquel Alonso Fernández   | r.alonsofe.2017@alumnos.urjc.es |
|                           |                                 |

## Support links :link: <a name="supplink"></a>
We're using [Trello](https://trello.com/b/CyhfEwRF/daw-g13) to make easier the organization for the web product.

### Web Access :computer:
>*Downloading the repository you will be able to view the latest things added.*

When you have downloaded the folder **wepapp8**, click on **code** and execute *Index.html* If you want to see the Main page.
Through it, you can access to the others! 

# Our project :construction_worker: :hammer: <a name="intro"></a>
### Introduction 
Instarecipes will be an application web where you can share all of your favorites recipes with the other users.
You can also follow or unfollow the users whenever you want to! And, if you really love one recipe, just add it to your favorites list.
And even if you don't want to register you will be able to see everyone's recipes.

  #### Main page 
  <p align="center">
   <img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Gifs/Main-page.gif">
  </p> 
  
  #### Publish recipe
  <p align="center">
   <img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Gifs/Publish-recipe.gif">  
  </p>  
  
  #### Filter per ingredients, category...
  <p align="center">
   <img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Gifs/Filtered-search.gif">
  </p>
  
  #### Search a recipe
  <p align="center">
   <img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Gifs/Search-navbar.gif">
  </p>  
  
  #### Profile page
  <p align="center">
   <img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Gifs/Profile-page(rs).gif">
  </p>
  
  #### Recipe page
   <p align="center">
    <img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Gifs/Recipe-page(rs).gif">
   </p>  
   
### Funcionalities <a name="funcionalities"></a>
Here you can found a list of existing features in Instarecipes.
> * Posibility to upload a recipe. 
> * Posibility to download a recipe.
> * Option to have a favorites recipes list.
> * Posibility of filtering recipes by ingredients.
>    * Be able to erase *all* selected ingredients.
> * Interaction between users.
>    * Comments section.
>    * Follow and unfollow available.
> * Divide recipes by categories.
> * Add visual elements (videos, images...).
> * Trending recipes. *There will be a ranking with 3 recipes with the highest amount of likes in a certain time.*
> * Posibility of Sign up. 
>    * By Facebook, Google or email.
> * Ban available to users. *Because of 'trolls' comments or recipes.*

### Entities <a name="entities"></a>

* User :bust_in_silhouette: 

* Recipe :hamburger:

* Steps :1234:

* Ingredient :bread:

* Cooking Style :seedling:

* Category :open_file_folder:

* Comment :speech_balloon:

### Types of users and permissions <a name="users"></a>
There are three types of users:
* **Anonymous.**   <a name="anonymousUser"></a>
They won't be able to upload any recipes or post comments, but, they can view recipes, search users, filter by ingredients, allergens, cooking style or even search one recipe. 
Neither can they follow or unfollow users or have a list of favorite recipes, give likes... but, they do have the option to download recipes with all the steps, ingredients, and photos.  
  * **Permissions.**  
   An anonymous user always has the option to download a recipe and see what users upload without problem.
* **Registered.**  <a name="registeredUser"></a>
They can upload and delete his own recipes. Obviously they have the option of download a recipe, search users, use the filter option, cooking style...
  * **Permissions.**
    If a registered user upload a recipe, it will have a comments section, but if someone comments something and the owner does not like it, he has the option to delete it.
    Also, they can edit the recipe post later, so they don't have to delete and upload it again. This means: uploading new photos, modify the steps...  
* **Administrator.**  <a name="adminUser"></a>
There is only one admin, and he will be the one that has full control over the information in the Web.
  * **Permissions.**  
  Receive requests from users about new ingredients that can be added, cooking style and categories. 
  In addition, he can also delete recipes, users and comments that he considers inappropiate.

### Complementary technology  
One of the complementary technologies that we will use is the possibility of **logging in without email.**  
It is almost always easier to sign up for a new site by accessing through your **Google, Facebook or even Twitter**, and the good thing about this is that practically almost everyone has a Google account, so we will facilitate the registration to new users with a **simple click**.

<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/google-icon.png" width="50" height="50"> <img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/facebook-icon.png" width="50" height="50"> <img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/twitter-icon.png" width="50" height="50"> 
</p>  

### Algorithm or advanced query  <a name="algorithm"></a>
> In this section we will make a brief explanation of what our advanced search algorithm will consist of.  

The first thing we observe when we visit the main page are **three slides**, which contain three outstanding recipes at a certain time and, that's simple but, the funny thing about this is that, each registered user can emphasize that he is allergic to certain foods so, if one of the trending recipes of the moment contains an allergen that the user has, **another recipe will be shown to him.**

<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Gifs/Trending-gif.gif">  
</p>  

### Navigation diagrams <a name="navDiagram"></a>
Finally, we show the *first* navigation diagrams to make the connections between the pages a little bit clearer. 

* **Main diagram** 
<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Navigation%20Diagrams/Navigation%20Diagram%20(Pages).png">
</p> 

In the diagram we can see above, we observe that Home, User Profile, Ranking, Search User/Recipe and Login/Sign Up are always connected to each other, this is simply because they are in the navbar, ***but*** Login/Sign Up button has an arrow directed towards it, this means that you can go to the login and sign up section but once you're there, you cannot go to User Profile, Ranking and Search User/Recipe because there is no Navbar there. (*Obviously, you can go to the Home page* :house:) 

* **Users and profile diagram**  
In the next diagram, you can see the main differences between the registered users, anonymous users and the administrator.
All of them can access to their recipes, the recipes pages, go back to home... (*Main diagram*) **BUT** the administrator will have a menu where he can manage things like accept new ingredients, categories...

<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Navigation%20Diagrams/Navigation%20Diagram%20(Profile).png">
</p> 



# Web with HTML generated on server and AJAX <a 

## Most important commits :newspaper: <a name="importantComm"></a>
|                           |                                 |                                           |
|---------------------------|---------------------------------|-------------------------------------------|
| Hamsa Aldrobi Elharti     | h.aldrobi@alumnos.urjc.es       |[@lordkener](https://github.com/lordkener) |
|                           |                                 |                                           |

> Textual description of the tasks performed in the phase.
> * Ranking and AJAX button.  
> * Spring Security.  
> * Init database.  
> * Reordered folders.  
> * 'SEE DESCRIPTION' commit: user's publications, likes, comments...

|                           |                                 |                                             |
|---------------------------|---------------------------------|---------------------------------------------|
| Manuel León Briz          | m.leon.2016@alumnos.urjc.es     | [@manuellb97](https://github.com/manuellb97)|
|                           |                                 |                                             |

> This developer has been doing tasks which are focus on user's experience.
> * Graphics done!.   
> * Likes funcionality. 
> * Advanced Algorythm. 
> * Reorder folders.  
> * Navbar funcionalities. 

|                           |                                 |                                            |
|---------------------------|---------------------------------|--------------------------------------------|
| José María Melero Gimenez | jm.melero.2016@alumnos.urjc.es  | [@trevod98](https://github.com/trevod98)   |
|                           |                                 |                                            |

> Textual description of the tasks performed in the phase.
> * Images to BBDD.  
> * Category, comment step and cooking style java classes.  
> * Each step images. 
> * Commit 4  
> * Commit 5 

|                           |                                 |                                            |
|---------------------------|---------------------------------|--------------------------------------------|
| Raquel Alonso Fernández   | r.alonsofe.2017@alumnos.urjc.es | [@Muffinous](https://github.com/Muffinous) |
|                           |                                 |                                            |

> Textual description of the tasks performed in the phase.
> * Filtered search button.  
> * Conection between filtered search button and search page.
> * Managment of new users (admin)  
> * Database conections.  
> * Google Javascript and login. 

|                           |                                 |                                            |
|---------------------------|---------------------------------|--------------------------------------------|
| Daniel Murillo Garzón     | d.murillo.2018@alumnos.urjc.es  | [@dMurill0](https://github.com/dMurill0)   |
|                           |                                 |                                            |

> Textual description of the tasks performed in the phase.
> * Upload recipe container.      
> * Login and sign up restrictions.  
> * User settings button.                      
> * Validated email.  
> * Responsive pages.


## Most worked files :newspaper: <a name="workedFiles"></a>
|                           |                                 |                                 |
|---------------------------|---------------------------------|---------------------------------|
| Daniel Murillo Garzón     | d.murillo.2018@alumnos.urjc.es  |                                 |
| Hamsa Aldrobi Elharti     | h.aldrobi@alumnos.urjc.es       |                                 |
| Manuel León Briz          | m.leon.2016@alumnos.urjc.es     |                                 |
| José María Melero Gimenez | jm.melero.2016@alumnos.urjc.es  |                                 |
| Raquel Alonso Fernández   | r.alonsofe.2017@alumnos.urjc.es |                                 |
|                           |                                 |                                 |  



## Instructions <a name="instructions"></a>  
To access the website, you need:

 * Any environment that is for web server development technologies
> We recommend [Visual Studio Code](https://code.visualstudio.com), since it has been the code editor with which we have developed the project.
 * MySQL server community.
 * MySQL workbench.
> * Once we have both downloaded and installed (*follow the steps indicated by the installer and once you have to enter the password for the database you have to put: '**root**'*).  
> * Now that we have Workbench, access to your MySQL connections (*Local instance MySQL80 with the word **root***) and create a new Schema named: "instarecipes_db".    
> * Finally, go to Visual Studio Code (*or the program you have downloaded*) and you must download the following dependencies:     
>>  * Spring Boot Extension Pack.  
>>  * Java Extension Pack.  
>>  * Maven for Java.  
> * Click 'Start..' button, go to your navegator and put: http://localhost:8443 and enjoy! (***We will change it to https soon***)

## Navigation Diagrams <a name="navDiagrams2"></a>

<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Diagrams/Navigation%20Diagram%20(Pages-Updated).png"></p> 

## Database diagrams <a name="dbDiagram"></a>

<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Diagrams/databaseDiagram.png"
</p> 

##  Classes and templates diagrams </a><a name="CTdiagrams"></a>
