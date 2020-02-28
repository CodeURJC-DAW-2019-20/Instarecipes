function parentComment(user, c_id){
    var field = document.getElementById("areaComment");
    field.value = "";
    var pComment = document.getElementById("userComment"+user);
    field.value = pComment.value + " ";
    var auxChar = c_id;
    var c_idComment = document.getElementById("parentComment");
    c_idComment.value = auxChar;
    console.log("El comentario: " + auxChar)
}
function commentCountChar(val) {
    var len = val.value.length;
    if (len > 500) {
      val.value = val.value.substring(0, 500);
    } else {
      $('#commentCountChar').text(500 - len);
    }
  };