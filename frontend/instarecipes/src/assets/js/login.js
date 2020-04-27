var signUp = document.getElementById('signUp');
var divSignUp = document.getElementById('signUpDiv');
var divCompleteSignUp = document.getElementById('completeSignUpDiv');
var emailInfo = document.getElementById('email');
var emailMsg = document.getElementById('email-val');
var pass = document.getElementById('pass');
var cpass = document.getElementById('cpass');
var divPass = document.getElementById('conf-pass');
var nopass = document.getElementById('nopass');
signUp.onclick = function () {

    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if (emailInfo.value.match(mailformat) && (pass.value.localeCompare(cpass.value) == 0) && !(pass.value == "")) {
        divCompleteSignUp.style.display = "block";
        divSignUp.style.display = "none";
    }
    else if (emailInfo.value.match(mailformat)) {
        emailMsg.style.display = "none";
        if ((pass.value == ""))
            nopass.style.display = "block";
        else if (pass.value.localeCompare(cpass.value) != 0)
            divPass.style.display = "block";

    }
    else if (pass.value == "") {
        nopass.style.display = "block";
        return true;
    }
    else if (pass.value.localeCompare(cpass.value) != 0) {
       
        divPass.style.display = "block";
        if (!emailInfo.value.match(mailformat)) {
            emailMsg.style.display = "block";
        }
        if (pass.value == "") {
            nopass.style.display = "block";
        }
        return true;
    }
    
    else if (!emailInfo.value.match(mailformat)) {
        emailMsg.style.display = "block";
        return true;
    }

    return false;


}

