var completeProfile = document.getElementById('completeProfile');
var divCompleteProfile = document.getElementById('completeProfileDiv');
var revert = false;
completeProfile.onclick = function(){
    if(!revert){
        divCompleteProfile.style.display="block";
        revert = true;
    }else{
        divCompleteProfile.style.display="none";
        revert = false;
    }
    
}
