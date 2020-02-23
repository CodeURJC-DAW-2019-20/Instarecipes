var counter = 2;
var arrayOfQuantity = 
    "<option>1 Ud.</option>"
        +"<option>2 Ud.</option>"
        +"<option>5 Ud.</option>"
        +"<option>10 Ud.</option>"
        +"<option>50 Ud.</option>"
        +"<option>25 g./cl.</option>"
        +"<option>50 g./cl.</option>"
        +"<option>100 g./cl.</option>"
        +"<option>250 g./cl.</option>"
        +"<option>1/2 kg./l.</option>"
        +"<option>1 kg./l.</option>"
        +"<option>5 kg./l.</option>";
var arrayOfIngredients = 
    "<option>Bread</option>"
        +"<option>Mushrooms</option>"
        +"<option>Beans</option>"
        +"<option>Nuts</option>"
        +"<option>Salad</option>"
        +"<option>Tomatoe</option>";
var arrayOfCategories =
    "<option>Vegetarian</option>"
        +"<option>Athlantic</option>"
        +"<option>Vegan</option>"
        +"<option>Dessert</option>"
        +"<option>Gluten Free</option>"
        +"<option>Sugar Free</option>";
$(document).ready(function() {
    //------------------ ADDING AN INGREDIENT ------------------//
    $("#add-ingredient").click(function() {
      var lastField = $("#ingredients-form div:last");
      var intId = (lastField && lastField.length && lastField.data("idx") + 1) || 1;
      var fieldWrapper = $("<div class=\"row\" id=\"ingredients-field" + intId + "\"/>");
      fieldWrapper.data("idx", intId);
      //var fName = $("<input type=\"text\" class=\"fieldname col-4\" />");
      var fQuantity = $("<select class=\"col-3 avaiable-quantity\">"
          +arrayOfQuantity
        +"</select>"
      );
      var fIngredients = $("<select name=\"ingredients\" class=\"col-7 avaiable-ingredients\">"
                +arrayOfIngredients
            +"</select>"
      );
      var removeButton = $("<div class=\"btn-danger col-1\" style=\"border-radius:0.5rem;"
        +"align-self:center;"
        +"text-align:center;"
        +"padding:0;"
        +"cursor:pointer\">"
        +"<i class=\"fa fa-trash\" aria-hidden=\"true\" styles=\"object-fit:cover;padding:50%\"></i>"
        +"</div>");
      removeButton.click(function() {
          $(this).parent().remove();
      });
      fieldWrapper.append(fQuantity);
      fieldWrapper.append(fIngredients);
      fieldWrapper.append(removeButton);
      $("#ingredients-form").prepend(fieldWrapper);//add to the top of the form
    });
    //------------------ ADDING A STEP ------------------//
    $("#add-step").click(function() {
        var lastField = $("#steps-form div:last");
        var intId = (lastField && lastField.length && lastField.data("idx") + 1) || 1;
        var fieldWrapper = $("<div id=\"steps-field" 
        + intId + "\">"
        +"<h3>Step " + counter +"</h3><hr></div>");
        counter++;
        fieldWrapper.data("idx", intId);
        var fStep = $("<div style=\"width: 100%;height:250\">"
            +"<div id=\"stepsCountChar"+counter+"\"></div>"
            +"<textarea onkeyup=\"stepsCountChar(this)\" class=\"\""
                +"placeholder=\"Type how to do this step\" style=\"width: 100%;"
                +"height: 150px;border-radius: 0.5rem;"
                +"border:1px solid rgba(0, 0, 0, 0.3);"
                +"padding:10px;\">"
            +"</textarea>"
            +"<div style=\"width:100%;height:100%;display:flex;flex-wrap:wrap;right:0\">"
            +"<div class=\"btn-outline-primary\" style=\"border-radius:0.5rem;border:1px solid rgb(23, 162, 184);background-color:white"
                    +"align-self:center;"
                    +"text-align:center;"
                    +"padding:0;"
                    +"right:0;"
                    +"width:25%;"
                    +"position:absolute;"
                    +"cursor:pointer\">"
                +"<i class=\"fa fa-camera\" aria-hidden=\"true\" styles=\"object-fit:cover;padding:5px\"></i>"
            +"</div>"
        +"</div>"
        );
        var removeButton = $("<div class=\"btn-danger\" style=\"border-radius:0.5rem;"
                +"align-self:center;"
                +"text-align:center;"
                +"width:10%;"
                +"cursor:pointer\">"
                +"<i class=\"fa fa-trash\" aria-hidden=\"true\" styles=\"object-fit:cover;"
                +"padding:5px\"></i>"
            +"</div>"
        );
        removeButton.click(function() {
            counter--; //still've some troubles, need to refresh with ajax(realtime)the counter var for each h3 on top
            $(this).parent().remove();
        });
        fieldWrapper.append(fStep);
        fieldWrapper.append(removeButton);
        //fieldWrapper.append(removeButton);
        $("#steps-form").append(fieldWrapper);//add to the top of the form
      });
    //------------------------------------------------------//
    $("#post-recipe").click(function(){
      console.log("valores de los cojones: " + $("#ingredients-form").val());
      document.getElementById("ingredientsId").setAttribute("value", $("#ingredients-form").val());
    });
    //------------------ ADDING A CATEGORY ------------------//
    $("#add-category").click(function() {
        var lastField = $("#category-form div:last");
        var intId = (lastField && lastField.length && lastField.data("idx") + 1) || 1;
        var fieldWrapper = $("<div class=\"row\" id=\"category-field" + intId + "\"/>");
        fieldWrapper.data("idx", intId);
        var fCategories = $("<select class=\"col-9 avaiable-categories\">"
                +arrayOfCategories
            +"</select>"
        );
        var removeButton = $("<div class=\"btn-danger col-2\" style=\"border-radius:0.5rem;"
          +"align-self:center;"
          +"text-align:center;"
          +"padding:0;"
          +"cursor:pointer\">"
          +"<i class=\"fa fa-trash\" aria-hidden=\"true\" styles=\"object-fit:cover;padding:50%\"></i>"
          +"</div>");
        removeButton.click(function() {
            $(this).parent().remove();
        });
        fieldWrapper.append(fCategories);
        fieldWrapper.append(removeButton);
        $("#category-form").prepend(fieldWrapper);
      });

    //--------------------------------------------------------//
  });
  function stepCountChar(val) {
    var len = val.value.length;
    if (len > 500) {
      val.value = val.value.substring(0, 500);
    } else {
      $('#stepCountChar').text(500 - len);
    }
  };
  function introCountChar(val) {
    var len = val.value.length;
    if (len > 200) {
      val.value = val.value.substring(0, 200);
    } else {
      $('#introCountChar').text(200 - len);
    }
  };
  function titleCountChar(val) {
    var len = val.value.length;
    if (len > 50) {
      val.value = val.value.substring(0, 50);
    } else {
      $('#titleCountChar').text(50 - len);
    }
  };
  function stepsCountChar(val) {
    var len = val.value.length;
    if (len > 500) {
      val.value = val.value.substring(0, 500);
    } else {
      $('#stepsCountChar'+counter).text(500 - len);
    }
  };
  var easy = $("#easy-star");
  var medium = $("#medium-star");
  var hard = $("#hard-star");
  var extreme = $("#extreme-star");

  function starClick(input){
    switch(input){
        case 1:
            $("#easy-text").removeAttr("hidden");
            $("#medium-text").attr("hidden",true);
            $("#hard-text").attr("hidden",true);
            $("#extreme-text").attr("hidden",true);

            easy.attr("name", "difficulty");
            medium.removeAttr("name");
            hard.removeAttr("name");
            extreme.removeAttr("name");
            break;
        case 2:
            $("#easy-text").attr("hidden",true);
            $("#medium-text").removeAttr("hidden");
            $("#hard-text").attr("hidden",true);
            $("#extreme-text").attr("hidden",true);

            easy.removeAttr("name");
            medium.attr("name", "difficulty");
            hard.removeAttr("name");
            extreme.removeAttr("name");
            break;
        case 3:
            $("#easy-text").attr("hidden",true);
            $("#medium-text").attr("hidden",true);
            $("#hard-text").removeAttr("hidden");
            $("#extreme-text").attr("hidden",true);

            easy.removeAttr("name");
            medium.removeAttr("name");
            hard.attr("name", "difficulty");
            extreme.removeAttr("name");
            break;
        case 4:
            $("#easy-text").attr("hidden",true);
            $("#medium-text").attr("hidden",true);
            $("#hard-text").attr("hidden",true);
            $("#extreme-text").removeAttr("hidden");

            easy.removeAttr("name");
            medium.removeAttr("name");
            hard.removeAttr("name");
            extreme.attr("name", "difficulty");
            break;
        default:
            $("#easy-text").attr("hidden",true);
            $("#medium-text").attr("hidden",true);
            $("#hard-text").attr("hidden",true);
            $("#extreme-text").attr("hidden",true);
    }
};