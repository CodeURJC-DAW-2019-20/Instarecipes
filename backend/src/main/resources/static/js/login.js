var signUp = document.getElementById('signUp');
var divSignUp = document.getElementById('signUpDiv');
var divCompleteSignUp = document.getElementById('completeSignUpDiv');
var emailInfo = document.getElementById('email');
var emailMsg = document.getElementById('email-val');
signUp.onclick = function(){
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if (emailInfo.value.match(mailformat)) {
        divCompleteSignUp.style.display = "block";
        divSignUp.style.display = "none";
        return true;
    }
    else {
        emailMsg.style.display = "block";
        return false;
    }
    
    
}
