// $('#google-button').on('click', function() {
// 	// Initialize with your OAuth.io app public key
// 	OAuth.initialize('43F7yt0sbkJpqhBlCWASWPQtsos');
//   OAuth.popup('google').then(google => {
//     console.log('google:',google);
//     // Retrieves user data from oauth provider
//     // Prompts 'welcome' message with User's email on successful login
//     // #me() is a convenient method to retrieve user data without requiring you
//     // to know which OAuth provider url to call
//     google.me().then(data => {
//       console.log('me data:', data);
//       alert('Google says your email is:' + data.email + ".\nView browser 'Console Log' for more details");
// 	  });
//     // Retrieves user data from OAuth provider by using #get() and
//     // OAuth provider url
//     google.get('https://people.googleapis.com/v1/people/me?personFields=names,emailAddresses,occupations,organizations,addresses,locales').then(data => {
//       console.log('self data:', data);
//     })
// 	});
// })

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
    // Useful data for your client-side scripts:
    var profile = googleUser.getBasicProfile();
    console.log("ID: " + profile.getId()); // Don't send this directly to your server!
    console.log('Full Name: ' + profile.getName());
    console.log('Given Name: ' + profile.getGivenName());
    console.log('Family Name: ' + profile.getFamilyName());
    console.log("Image URL: " + profile.getImageUrl());
    console.log("Email: " + profile.getEmail());
    console.log("username: ", profile.getGivenName()+profile.getFamilyName());
    // The ID token you need to pass to your backend:
    var id_token = googleUser.getAuthResponse().id_token;
    console.log("ID Token: " + id_token);
  }

  function signOut() {
    gapi.auth2.getAuthInstance().signOut().then(function () {
      console.log('User signed out.');
      location.reload();
    });

} 