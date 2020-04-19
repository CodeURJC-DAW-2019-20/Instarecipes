import {Component, OnInit, ElementRef, ViewChild, ɵConsole} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AuthenticationService } from '../services/authentication.service';
import { User } from '../Interfaces/user.model';
import { ProfileService } from '../services/profile.service';


@Component({
  // styleUrls: ['./login.component.css'],
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit{
   loginForm: FormGroup;
   submitted = false;
   returnUrl: string;
   error: '';
   auth2: any;
   user: User;
   urlAvatar;

   password: string;
   @ViewChild('loginRef', {static: true }) loginElement: ElementRef;

   constructor(
       private formBuilder: FormBuilder,
       private route: ActivatedRoute,
       private router: Router,
       private authenticationService: AuthenticationService,
       private profileService: ProfileService,
       ) {
        this.user = {username: null, email: '', password: '', name: '', surname: '', info: 'Hello world!', allergens: null};
       }

   ngOnInit() {
    this.googleSDK();

      this.loginForm = this.formBuilder.group({
           username: ['', Validators.required],
           password: ['', Validators.required]
       });

       // get return url from route parameters or default to '/'
       this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/index';
   }

  // // convenience getter for easy access to form fields
   get f() { return this.loginForm.controls; }

   onSubmit() {
       this.submitted = true;

       // stop here if form is invalid
       if (this.loginForm.invalid) {
           return;
       }

       this.authenticationService.login(this.f.username.value, this.f.password.value)
           .pipe(first())
           .subscribe(
               data => {
                   this.router.navigate([this.returnUrl]);
               },
               error => {
                   this.error = error;
               });
   }


   googleSDK() {
    window['googleSDKLoaded'] = () => {
      window['gapi'].load('auth2', () => {
        this.auth2 = window['gapi'].auth2.init({
          client_id: '30926288382-c62nfudjcsagie7un1seer0uhmcgteok.apps.googleusercontent.com',
          cookiepolicy: 'single_host_origin',
          scope: 'profile email'
        });

        this.prepareLoginButton();
      });
    }

    (function(d, s, id){
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) {return;}
      js = d.createElement(s); js.id = id;
      js.src = "https://apis.google.com/js/platform.js?onload=googleSDKLoaded";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'google-jssdk'));

  }



  prepareLoginButton() {
    this.auth2.attachClickHandler(this.loginElement.nativeElement, {},
      (googleUser) => {
        let profile = googleUser.getBasicProfile();
        console.log('Token || ' + googleUser.getAuthResponse().id_token);
        console.log('ID: ' + profile.getId());
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName())
        this.setUser(profile.getGivenName()+profile.getFamilyName(), profile.getEmail(), "googlepass",  profile.getGivenName(),  profile.getFamilyName(), null, profile.getImageUrl());
      }, (error) => {
        alert(JSON.stringify(error, undefined, 2));
      });
  }

  setUser (username: string, email: string, password:string, name: string, surname: string, allergen: string, image: any) {
    this.user["username"] = username;
    this.user['email'] = email;
    this.user['password'] = password;
    this.user['name'] = name;
    this.user['surname'] = surname;
    this.user['allergens'] = allergen;

    console.log("este es mi usuario : ", this.user);
    this.onGoogleLogin(image);
  }

  onGoogleLogin(profilePic: any){
    console.log("entro en ongooglelogin");
    this.authenticationService.register(this.user)
    .subscribe(
      data => {
        console.log("User created!");
          this.authenticationService.login(this.user.username, this.user.password)
           .subscribe(
               data => {
                 console.log("User logged!");
                 this.googleAvatar(profilePic);
                //  this.profileService.updateProfileAvatar(profilePic).subscribe(
                //   imagen=>{
                //   },
                //     (error: Error) => console.log("File uploaded!")
                //  );
                this.router.navigate(["/index"]);
               },
               error => {
                   this.error = error;
               });
      },
      error => {
          alert("Try again");
          this.error = error;
      });

  }


  googleAvatar(profilePic: any) {
    var blob = new Blob([profilePic], {type: 'image/jpeg'});
    var file = new File([blob], 'googleAvatar.jpeg');
    console.log(file);
    this.profileService.updateProfileAvatar(file).subscribe(
      imagen=>{
      },
        (error: Error) => console.log("File uploaded!")
      );
  }

}
