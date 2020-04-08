$(document).ready(function() {
    document.getElementById('file-input').onchange = function(evt) {
        var tgt = evt.target || window.event.srcElement,
            files = tgt.files;
        // FileReader support
        if (FileReader && files && files.length) {
            var fr = new FileReader();
            fr.onload = function() {
                document.getElementById("no-such-image").setAttribute("hidden", "");
                document.getElementById("preview-img-file").removeAttribute("hidden");
                document.getElementById("preview-img-file").src = fr.result;
            }
            fr.readAsDataURL(files[0]);
        }
    }
    var image_val = document.getElementById("image-validation");
    var ingredient_val = document.getElementById("ingredient-val");

    image_val.style.display = "none";
    $(document).ready(function() {

        $('#post-recipe').bind("click", function() {
            var imgVal = $('#file-input').val();
            var ingredient = $('#ingredientsString').val();
            if ((imgVal == '') && (ingredient == '')) {
                image_val.style.display = "block";
                ingredient_val.style.display = "block";
            } else if (imgVal == '') {
                image_val.style.display = "block";
                if (ingredient != '')
                    ingredient_val.style.display = "none";
                else if (ingredient == '') {
                    ingredient_val.style.display = "block";
                }
            } else if (imgVal != '') {
                image_val.style.display = "none";
                if (ingredient != '') {
                    ingredient_val.style.display = "none";
                } else if (ingredient == '') {
                    ingredient_val.style.display = "block";
                }
            }
        });
    });


    var iList = document.getElementById("ingredientsList").value;
    var arrayOfIngredients = iList.split(",");

    var cList = document.getElementById("categoriesList").value;
    var arrayOfCategories = cList.split(",");

    var arrayIngredients = [];
    var arrayCategories = [];
    var arraySteps = [];
    var ingCounter = 0;
    var catCounter = 0;
    var stepCounter = 0;

    var ingredientsContainer = "";
    var categoriesContainer = "";

    for (var i = 0; i < arrayOfIngredients.length - 1; i++) {
        ingredientsContainer = ingredientsContainer + "<option>" + arrayOfIngredients[i] + "</option>";
    }
    for (var i = 0; i < arrayOfCategories.length - 1; i++) {
        categoriesContainer = categoriesContainer + "<option>" + arrayOfCategories[i] + "</option>";
    }

    //------------------ ADDING AN INGREDIENT ------------------//
    $("#add-ingredient").click(function() {
        console.log("ingredientes: " + iList);
        console.log("categorias: " + cList);
        var lastField = $("#ingredients-form div:last");
        var intId = (lastField && lastField.length && lastField.data("idx") + 1) || 1;
        var fieldWrapper = $("<div class=\"row\" id=\"ingredients-field" + intId + "\"/>");
        fieldWrapper.data("idx", intId);
        var fIngredients = $("<select id=\"ing" + ingCounter + "\" name=\"ing" + ingCounter + "\" class=\"col-9 avaiable-ingredients\">" + ingredientsContainer + "</select>");
        var removeButton = $("<div class=\"btn-danger col-1\" style=\"border-radius:0.5rem;" +
            "align-self:center;" +
            "text-align:center;" +
            "padding:0;" +
            "cursor:pointer\">" +
            "<i class=\"fa fa-trash\" aria-hidden=\"true\" styles=\"object-fit:cover;padding:50%\"></i>" +
            "</div>");
        fieldWrapper.append(fIngredients);
        fieldWrapper.append(removeButton);
        $("#ingredients-form").prepend(fieldWrapper); //add to the top of the form
        removeButton.click(function() {
            $(this).parent().remove();
        });
        ingCounter++;
    });

    //------------------ ADDING A CATEGORY ------------------//
    $("#add-category").click(function() {
        var lastField = $("#category-form div:last");
        var intId = (lastField && lastField.length && lastField.data("idx") + 1) || 1;
        var fieldWrapper = $("<div class=\"row\" id=\"category-field" + intId + "\"/>");
        fieldWrapper.data("idx", intId);
        var fCategories = $("<select id=\"cat" + catCounter + "\" name=\"ing" + catCounter + "\" class=\"col-9 avaiable-categories\">" + categoriesContainer + "</select>");
        var removeButton = $("<div class=\"btn-danger col-2\" style=\"border-radius:0.5rem;" +
            "align-self:center;" +
            "text-align:center;" +
            "padding:0;" +
            "cursor:pointer\">" +
            "<i class=\"fa fa-trash\" aria-hidden=\"true\" styles=\"object-fit:cover;padding:50%\"></i>" +
            "</div>");
        fieldWrapper.append(fCategories);
        fieldWrapper.append(removeButton);
        $("#category-form").prepend(fieldWrapper);
        removeButton.click(function() {
            $(this).parent().remove();
        });
        catCounter++;
    });

    var stepNumber = 2;
    //------------------ ADDING A STEP ------------------//
    $("#add-step").click(function() {
        var lastField = $("#steps-form div:last");
        var intId = (lastField && lastField.length && lastField.data("idx") + 1) || 1;
        var fieldWrapper = $("<div id=\"steps-field" +
            stepCounter + "\">" +
            "<h3>Step " + stepNumber + "</h3><hr></div>");
        fieldWrapper.data("idx", intId);
        var fStep = $("<div style=\"width: 100%;height:250\">" +
            "<div id=\"stepsCountChar" + stepNumber + "\"></div>" +
            "<textarea id=\"step" + stepCounter + "\" name=\"step" + stepCounter + "\" onkeyup=\"stepsCountChar(this)\" class=\"\"" +
            "placeholder=\"Type how to do this step\" style=\"width: 100%;" +
            "height: 150px;border-radius: 0.5rem;" +
            "border:1px solid rgba(0, 0, 0, 0.3);" +
            "padding:10px;\">" +
            "</textarea>" +
            "<div style=\"width:100%;height:100%;display:flex;flex-wrap:wrap;right:0\">" +
            "<label for=\"file-input" + stepCounter + "\" class=\"btn-outline-primary\" style=\"border-radius:0.5rem;border:1px solid rgb(23, 162, 184);background-color:white" +
            "align-self:center;" +
            "text-align:center;" +
            "padding:0;" +
            "right:0;" +
            "width:25%;" +
            "position:absolute;" +
            "cursor:pointer\">" +
            "<input id=\"file-input" + stepCounter + "\" name=\"allImages\" hidden type=\"file\" accept=\".jpg, .jpeg\">" +
            "<i class=\"fa fa-camera\" aria-hidden=\"true\" styles=\"object-fit:cover;padding:5px\"></i>" +
            "</input>" +
            "</label>" +
            "</div>"
        );
        var removeButton = $("<div id=\"removeB" + stepCounter + "\" class=\"btn-danger\" style=\"border-radius:0.5rem;" +
            "align-self:center;" +
            "text-align:center;" +
            "width:10%;" +
            "cursor:pointer\">" +
            "<i class=\"fa fa-trash\" aria-hidden=\"true\" styles=\"object-fit:cover;" +
            "padding:5px\"></i>" +
            "</div>"
        );
        fieldWrapper.append(fStep);
        fieldWrapper.append(removeButton);
        stepNumber++;
        $("#steps-form").append(fieldWrapper); //add to the bottom of the form
        if (stepCounter > 0) {
            var au = stepCounter - 1;
            var asdf = $("#removeB" + au);
            asdf.hide();
        }
        removeButton.click(function() {
            if (stepNumber > 3) {
                asdf.show();
            }
            stepCounter--;
            stepNumber--; //still've some troubles, need to refresh with ajax(realtime)the counter var for each h3 on top
            $(this).parent().remove();
        });
        stepCounter++;
    });

    //------------------- POST RECIPE BUTTON ---------------------//
    $("#post-recipe").click(function() {
        //INGREDIENTS
        for (var i = 0; i < ingCounter; i++) {
            var valor1 = document.getElementById("ing" + i);
            console.log("ID: " + valor1);
            if (valor1 != null) {
                arrayIngredients.push(valor1.value);
            }
        }
        var ings = document.getElementById("ingredientsString");
        ings.setAttribute("value", arrayIngredients);
        arrayIngredients = [];
        console.log("Definitive Ings: " + ings.value);
        //CATEGORIES
        for (var i = 0; i < catCounter; i++) {
            var valor2 = document.getElementById("cat" + i);
            console.log("ID: " + valor2);
            if (valor2 != null) {
                arrayCategories.push(valor2.value);
            }
        }
        var cats = document.getElementById("categoriesString");
        cats.setAttribute("value", arrayCategories);
        arrayCategories = [];
        console.log("Definitive Cat: " + cats.value);
        //STEPS
        var div = $("#divImageSteps");
        var contain = document.getElementById("withImage");
        var cadena = "";
        for (var i = 0; i < stepNumber - 2; i++) {
            var valor3 = $("#step" + i);
            if (valor3 != null) {
                var img = $("#file-input" + i);
                if ((img.val() != null) && (img.val() != "")) {
                    cadena = cadena + "1,";
                } else {
                    cadena = cadena + "0,";
                }
                var othersStps = valor3.val();
                if (i < stepNumber - 3) {
                    othersStps = othersStps + "ab_12_45_3";
                }
                arraySteps.push(othersStps);
            }
        }
        contain.setAttribute("value", cadena);
        var steps = document.getElementById("stepsString");
        steps.setAttribute("value", arraySteps);
        arraySteps = [];
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
        $('#stepsCountChar' + counter).text(500 - len);
    }
};
var easy = $("#easy-star");
var medium = $("#medium-star");
var hard = $("#hard-star");
var extreme = $("#extreme-star");

function starClick(input) {
    switch (input) {
        case 1:
            $("#easy-text").removeAttr("hidden");
            $("#medium-text").attr("hidden", true);
            $("#hard-text").attr("hidden", true);
            $("#extreme-text").attr("hidden", true);

            easy.attr("name", "difficulty");
            medium.removeAttr("name");
            hard.removeAttr("name");
            extreme.removeAttr("name");
            break;
        case 2:
            $("#easy-text").attr("hidden", true);
            $("#medium-text").removeAttr("hidden");
            $("#hard-text").attr("hidden", true);
            $("#extreme-text").attr("hidden", true);

            easy.removeAttr("name");
            medium.attr("name", "difficulty");
            hard.removeAttr("name");
            extreme.removeAttr("name");
            break;
        case 3:
            $("#easy-text").attr("hidden", true);
            $("#medium-text").attr("hidden", true);
            $("#hard-text").removeAttr("hidden");
            $("#extreme-text").attr("hidden", true);

            easy.removeAttr("name");
            medium.removeAttr("name");
            hard.attr("name", "difficulty");
            extreme.removeAttr("name");
            break;
        case 4:
            $("#easy-text").attr("hidden", true);
            $("#medium-text").attr("hidden", true);
            $("#hard-text").attr("hidden", true);
            $("#extreme-text").removeAttr("hidden");

            easy.removeAttr("name");
            medium.removeAttr("name");
            hard.removeAttr("name");
            extreme.attr("name", "difficulty");
            break;
        default:
            $("#easy-text").attr("hidden", true);
            $("#medium-text").attr("hidden", true);
            $("#hard-text").attr("hidden", true);
            $("#extreme-text").attr("hidden", true);
    }
};