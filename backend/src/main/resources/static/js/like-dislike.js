document.querySelectorAll('.focusHeart').forEach((el) => pressLike(el));

function pressLike(ele1){
  ele1.addEventListener("click", function(){
    ele1.setAttribute('src', 'images/icons/like_pressed.png');
    unpressLike(ele1);
  });
}
function unpressLike(ele2){
  ele2.addEventListener("click", function(){
    ele2.setAttribute('src', 'images/icons/like_unpressed.png');
    pressLike(ele2);
  });
}