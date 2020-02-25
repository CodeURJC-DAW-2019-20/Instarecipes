function renderButton() {
  gapi.signin2.render('my-signin2', {
    'scope': 'profile email',
    'width': 240,
    'height': 50,
    'longtitle': true,
    'theme': 'dark',
    'onsuccess': onSuccess,
    'onfailure': onFailure
  });
}

function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();

    //THIS IS ONLY TO VERIFY!! 
  console.log("ID: " + profile.getId()); // Don't send this directly to your server!
  console.log('Full Name: ' + profile.getName());
  console.log('Given Name: ' + profile.getGivenName());
  console.log('Family Name: ' + profile.getFamilyName());
  console.log("Image URL: " + profile.getImageUrl());
  console.log("Email: " + profile.getEmail());
  console.log("username: ", profile.getGivenName()+profile.getFamilyName());
  console.log("auth", profile.getAuthInstance);
    // The ID token we need to pass to your backend but we won't lol:
  var id_token = googleUser.getAuthResponse().id_token;
  console.log("ID Token: " + id_token);  

    fullName = profile.getName();
    givenName = profile.getGivenName();
    familyName = profile.getFamilyName();
    imageUrl = profile.getImageUrl();
    email = profile.getEmail();
    //pass to HTML
    var full = document.getElementById("fullNameGoogleUser");
    var gvnName = document.getElementById("givenNameGoogleUser");
    var fmlyName = document.getElementById("familyNameUser");
    var imgURL = document.getElementById("profileImgGoogleUser");
    var mail = document.getElementById("emailGoogleUser");
    var isGUON = document.getElementById("googleUserON");

    full.setAttribute("value", fullName);
    gvnName.setAttribute("value", givenName);
    fmlyName.setAttribute("value", familyName);
    imgURL.setAttribute("value", imageUrl);
    mail.setAttribute("value", email);
    isGUON.setAttribute("value", "yes");

  }
 
  function signOut() {
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

    //doesn't work :(
    /*function passToHTML(fullName,givenName,familyName,imageUrl,email){
      var full = document.getElementById("fullNameGoogleUser");
      var gvnName = document.getElementById("givenNameGoogleUser");
      var fmlyName = document.getElementById("familyNameUser");
      var imgURL = document.getElementById("profileImgGoogleUser");
      var mail = document.getElementById("emailGoogleUser");
  
      full.setAttribute("value", fullName);
      gvnName.setAttribute("value", givenName);
      fmlyName.setAttribute("value", familyName);
      imgURL.setAttribute("value", imageUrl);
      mail.setAttribute("value", email);
    }*/