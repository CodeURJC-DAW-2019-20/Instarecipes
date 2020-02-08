# Group 13 of DAW   ![Active](https://img.shields.io/badge/version-0.1-blue)
This is the main project's repository, where you can found folders like **Code**'s folder, that contains all the stuff related to the application developing code, or the **Prototype**'s folder, where you will be able to see our first ideas and some sketches used to carry out the web application.

## Members :busts_in_silhouette:
|                           |                                 |
|---------------------------|---------------------------------|
| Daniel Murillo Garzón     | d.murillo.2018@alumnos.urjc.es  |
| Hamsa Aldrobi Elharti     | h.aldrobi@alumnos.urjc.es       |
| Manuel León Briz          | m.leon.2016@alumnos.urjc.es     |
| José María Melero Gimenez | jm.melero.2016@alumnos.urjc.es  |
| Raquel Alonso Fernández   | r.alonsofe.2017@alumnos.urjc.es |
|                           |                                 |

## Support links :link:
We're using [Trello](https://trello.com/b/CyhfEwRF/daw-g13) to make easier the organization for the web product.

### Web Access :computer:
>*Downloading the repository you will be able to view the latest things added.*

When you have downloaded the folder **wepapp8**, click on **code** and execute *Index.html* If you want to see the Main page.
Through it, you can access to the others! 

# Our project :construction_worker: :hammer:
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
   
### Funcionalities
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

### Entities 

* User :bust_in_silhouette: 

* Recipe :hamburger:

* Steps :1234:

* Ingredient :bread:

* Cooking Style :seedling:

* Category :open_file_folder:

* Comment :speech_balloon:

### Types of users and permissions
There are three types of users:
* **Anonymous.**   
They won't be able to upload any recipes or post comments, but, they can view recipes, search users, filter by ingredients, allergens, cooking style or even search one recipe. 
Neither can they follow or unfollow users or have a list of favorite recipes, give likes... but, they do have the option to download recipes with all the steps, ingredients, and photos.  
  * **Permissions.**  
   An anonymous user always has the option to download a recipe and see what users upload without problem.
* **Registered.**  
They can upload and delete his own recipes. Obviously they have the option of download a recipe, search users, use the filter option, cooking style...
  * **Permissions.**
    If a registered user upload a recipe, it will have a comments section, but if someone comments something and the owner does not like it, he has the option to delete it.
    Also, they can edit the recipe post later, so they don't have to delete and upload it again. This means: uploading new photos, modify the steps...  
* **Administrator.**  
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

### Algorithm or advanced query  
> In this section we will make a brief explanation of what our advanced search algorithm will consist of.  

The first thing we observe when we visit the main page are **three slides**, which contain three outstanding recipes at a certain time and, that's simple but, the funny thing about this is that, each registered user can emphasize that he is allergic to certain foods so, if one of the trending recipes of the moment contains an allergen that the user has, **another recipe will be shown to him.**

<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Gifs/Trending-gif.gif">  
</p>  

### Navigation diagrams
Finally, we show the navigation diagrams to make the connections between the pages a little bit clearer. 

* **Main diagram**
<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Navigation%20Diagrams/Navigation%20Diagram%20(Pages).png" width="50" height="50">
</p> 

* **Users and profile diagram**
In the diagram we can see above, we observe that Home, User Profile, Ranking, Search User/Recipe and Login/Sign Up are always connected to each other, this is simply because they are in the navbar, ***but*** Login/Sign Up button has an arrow directed towards it, this means that you can go to the login and sign up section but once you're there, you cannot go to User Profile, Ranking and Search User/Recipe because there is no Navbar there (*Obviously, you can go to the Home page :house: *) . 

In the next diagram, you can see the main differences between the registered users, anonymous users and the administrator.
All of them can access to their recipes, the recipes pages, go back to home... (*Main diagram*) **BUT** the administrator will have a menu where he can manage things like accept new ingredients, categories...

<p align="center">
<img src="https://github.com/CodeURJC-DAW-2019-20/webapp8/blob/master/Images/Navigation%20Diagrams/Navigation%20Diagram%20(Profile).png" width="50" height="50">
</p> 

