$(document).ready(function(){
    $("#load-recipe-btn a").click(function(){
        $("#recipes-container").load("recipe-container.html #more-recipes");
        return false;
    });

});