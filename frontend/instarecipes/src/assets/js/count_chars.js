function stepCountChar(val) {
    var len = val.value.length;
    if (len > 500) {
        val.value = val.value.substring(0, 500);
    } else {
        document.getElementById('stepCountChar').innerHTML = 500 - len;
    }
};

function introCountChar(val) {
    var len = val.value.length;
    if (len > 200) {
        val.value = val.value.substring(0, 200);
    } else {
        document.getElementById('introCountChar').innerHTML = 200 - len;
    }
};

function titleCountChar(val) {
    var len = val.value.length;
    if (len > 50) {
        val.value = val.value.substring(0, 50);
    } else {
        document.getElementById('titleCountChar').innerHTML = 50 - len;
    }
};

function stepsCountChar(val) {
    var len = val.value.length;
    if (len > 500) {
        val.value = val.value.substring(0, 500);
    } else {
        document.getElementById('stepsCountChar' + val.id).innerHTML = 500 - len;
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