  window.fbAsyncInit = function() {
    FB.init({
      appId            : 'InstaRecipes',
      autoLogAppEvents : true,
      xfbml            : true,
      version          : 'v6.0',
      status           : true,
      xfbml            : false,
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "https://connect.facebook.net/es_LA/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
