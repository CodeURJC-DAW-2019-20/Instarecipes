var signUp = document.getElementById('signUp');
var signIn = document.getElementById('signIn');
var divSignUp = document.getElementById('signupDiv');
var divSignIn = document.getElementById('loginDiv');
signUp.onclick = function(){
    divSignUp.style.display = "block";
    divSignIn.style.display = "none";
    return false;
}
signIn.onclick = function(){
    divSignUp.style.display = "none";
    divSignIn.style.display = "block";
    return false;
}
