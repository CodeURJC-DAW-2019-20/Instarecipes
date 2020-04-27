function ValidateEmail(inputText) {
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if (inputText.value.match(mailformat)) {
        console.log("You entered a valid mail");
        document.email.focus();
        return true;
    }
    else {
        console.log("You have entered an invalid email address!");
        document.email.focus();
        return false;
    }
}