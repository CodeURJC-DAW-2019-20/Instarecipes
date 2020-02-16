var signUp = document.getElementById('signUp');
var divSignUp = document.getElementById('signUpDiv');
var divCompleteSignUp = document.getElementById('completeSignUpDiv');
signUp.onclick = function(){
    divCompleteSignUp.style.display = "block";
    divSignUp.style.display = "none";
    return false;
}
