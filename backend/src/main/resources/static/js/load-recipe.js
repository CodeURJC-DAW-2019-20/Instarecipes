$(document).ready(function(){
    $("publications-item mx-auto img").click(function(){
        $("recipes-container").load("index.html #more-recipes");
        return false;
    });

});