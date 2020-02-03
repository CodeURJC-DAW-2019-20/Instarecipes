var verifyProfile = document.getElementById('verifyProfileBtn');
var verifyMsg = document.getElementById('verifyMsgDiv');
var validationDiv = document.getElementById('validationDiv');

verifyProfile.onclick = function(){
    verifyMsg.style.display= "block";
    validationDiv.style.display= "none";
   return false;
}