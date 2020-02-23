var signUp = document.getElementById('signUp');
var divSignUp = document.getElementById('signUpDiv');
var divCompleteSignUp = document.getElementById('completeSignUpDiv');
var emailInfo = document.getElementById('email');
var emailMsg = document.getElementById('email-val');
var pass = document.getElementById('pass');
var cpass = document.getElementById('cpass');
var divPass = document.getElementById('conf-pass');
signUp.onclick = function () {

    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if (emailInfo.value.match(mailformat) && pass.value.match(cpass.value)) {
        divCompleteSignUp.style.display = "block";
        divSignUp.style.display = "none";

    }
    else if (!pass.value.match(cpass.value)) {
        divPass.style.display = "block";

    }
    else if (!emailInfo.value.match(mailformat)) {
        emailMsg.style.display = "block";

    }

    return false;


}

