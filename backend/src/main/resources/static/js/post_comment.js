function parentComment(parent){
    var field = document.getElementById("areaComment");
    field.value = "";
    var pComment = document.getElementById("userComment"+parent);
    field.value = pComment.value + " ";
    var auxChar = parent;
    var parentComment = document.getElementById("parentComment");
    parentComment.value = auxChar;
}