$(document).ready(function(){
    $("publications-item mx-auto img").click(function(){
        $("recipes-container").load("recipe-container.html #more-recipes");
        return false;
    });

});