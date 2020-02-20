function contentDiv(){
    $("#content").removeAttr("hidden");
    $("#request-div").attr("hidden","true");
    $("#users-div").attr("hidden","true");
    $("#recipes-div").attr("hidden","true");
    $("#reports-div").attr("hidden","true");
    
    $("#text-content").attr("class", "active");
    $("#text-request").removeAttr("class");
    $("#text-users").removeAttr("class");
    $("#text-recipes").removeAttr("class");
    $("#text-reports").removeAttr("class");
  }
  function requestDiv(){
    $("#content").attr("hidden","true");
    $("#request-div").removeAttr("hidden");
    $("#users-div").attr("hidden","true");
    $("#recipes-div").attr("hidden","true");
    $("#reports-div").attr("hidden","true");

    $("#text-content").removeAttr("class");
    $("#text-request").attr("class", "active");
    $("#text-users").removeAttr("class");
    $("#text-recipes").removeAttr("class");
    $("#text-reports").removeAttr("class");
  }
  function usersDiv(){
    $("#content").attr("hidden","true");
    $("#request-div").attr("hidden","true");
    $("#users-div").removeAttr("hidden");
    $("#recipes-div").attr("hidden","true");
    $("#reports-div").attr("hidden","true");
    
    $("#text-content").removeAttr("class");
    $("#text-request").removeAttr("class");
    $("#text-users").attr("class", "active");
    $("#text-recipes").removeAttr("class");
    $("#text-reports").removeAttr("class");
  }
  function recipesDiv(){
    $("#content").attr("hidden","true");
    $("#request-div").attr("hidden","true");
    $("#users-div").attr("hidden","true");
    $("#recipes-div").removeAttr("hidden");
    $("#reports-div").attr("hidden","true");

    $("#text-content").removeAttr("class");
    $("#text-request").removeAttr("class");
    $("#text-users").removeAttr("class");
    $("#text-recipes").attr("class", "active");
    $("#text-reports").removeAttr("class");
  }
  function reportsDiv(){
    $("#content").attr("hidden","true");
    $("#request-div").attr("hidden","true");
    $("#users-div").attr("hidden","true");
    $("#recipes-div").attr("hidden","true");
    $("#reports-div").removeAttr("hidden");

    $("#text-content").removeAttr("class");
    $("#text-request").removeAttr("class");
    $("#text-users").removeAttr("class");
    $("#text-recipes").removeAttr("class");
    $("#text-reports").attr("class", "active");
  }