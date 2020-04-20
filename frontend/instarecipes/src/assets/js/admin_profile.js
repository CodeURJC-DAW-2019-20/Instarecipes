$(document).ready(function() {
    $("#contentDiv").click(function() {
        console.log("HOLAAAAAA???");
        $("#content").removeAttr("hidden");
        $("#request-div").attr("hidden", "true");
        $("#users-div").attr("hidden", "true");
        $("#recipes-div").attr("hidden", "true");
        $("#reports-div").attr("hidden", "true");

        $("#text-content").attr("class", "active");
        $("#text-request").removeAttr("class");
        $("#text-users").removeAttr("class");
        $("#text-recipes").removeAttr("class");
        $("#text-reports").removeAttr("class");
    });
    $("#requestDiv").click(function() {
        $("#content").attr("hidden", "true");
        $("#request-div").removeAttr("hidden");
        $("#users-div").attr("hidden", "true");
        $("#recipes-div").attr("hidden", "true");
        $("#reports-div").attr("hidden", "true");

        $("#text-content").removeAttr("class");
        $("#text-request").attr("class", "active");
        $("#text-users").removeAttr("class");
        $("#text-recipes").removeAttr("class");
        $("#text-reports").removeAttr("class");
    });
    $("#usersDiv").click(function() {
        $("#content").attr("hidden", "true");
        $("#request-div").attr("hidden", "true");
        $("#users-div").removeAttr("hidden");
        $("#recipes-div").attr("hidden", "true");
        $("#reports-div").attr("hidden", "true");

        $("#text-content").removeAttr("class");
        $("#text-request").removeAttr("class");
        $("#text-users").attr("class", "active");
        $("#text-recipes").removeAttr("class");
        $("#text-reports").removeAttr("class");
    });
    $("#recipesDiv").click(function() {
        $("#content").attr("hidden", "true");
        $("#request-div").attr("hidden", "true");
        $("#users-div").attr("hidden", "true");
        $("#recipes-div").removeAttr("hidden");
        $("#reports-div").attr("hidden", "true");

        $("#text-content").removeAttr("class");
        $("#text-request").removeAttr("class");
        $("#text-users").removeAttr("class");
        $("#text-recipes").attr("class", "active");
        $("#text-reports").removeAttr("class");
    });
    $("#reportsDiv").click(function() {
        $("#content").attr("hidden", "true");
        $("#request-div").attr("hidden", "true");
        $("#users-div").attr("hidden", "true");
        $("#recipes-div").attr("hidden", "true");
        $("#reports-div").removeAttr("hidden");

        $("#text-content").removeAttr("class");
        $("#text-request").removeAttr("class");
        $("#text-users").removeAttr("class");
        $("#text-recipes").removeAttr("class");
        $("#text-reports").attr("class", "active");
    });

    var itemCS = document.getElementById("itemsCS");
    var itemIng = document.getElementById("itemsIng");
    var itemCat = document.getElementById("itemsCat");

    $('#filter-item').on('change', function() {
        switch ($('#filter-item option:selected').val()) {
            case 'All':
                itemCS.removeAttribute("hidden");
                itemIng.removeAttribute("hidden");
                itemCat.removeAttribute("hidden");
                break;
            case 'Ingredients':
                itemCS.setAttribute("hidden", "true");
                itemIng.removeAttribute("hidden");
                itemCat.setAttribute("hidden", "true");
                break;
            case 'Categories':
                itemCS.setAttribute("hidden", "true");
                itemIng.setAttribute("hidden", "true");
                itemCat.removeAttribute("hidden");
                break;
            case 'Cooking style':
                itemCS.removeAttribute("hidden");
                itemIng.setAttribute("hidden", "true");
                itemCat.setAttribute("hidden", "true");
                break;
        }
    });
})
