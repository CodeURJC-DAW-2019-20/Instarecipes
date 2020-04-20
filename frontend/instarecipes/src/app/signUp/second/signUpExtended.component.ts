import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { ProfileService } from 'src/app/services/profile.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/Interfaces/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-second',
  templateUrl: './signUpExtended.component.html',
})
export class SignUpExtendedComponent implements OnInit {
  registerForm2: FormGroup;
  activate: boolean;
  allergens: Allergen [] = [];
  returnUrl: string;
  error: '';
  user: User;
  selectedFile: File;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private profileService: ProfileService,
    private router: Router,
    private authenticationService: AuthenticationService,
    ) {
      this.user = {username: null, email: '', password: '', name: '', surname: '', info: 'Hello world!', allergens: null};
    }

  ngOnInit() {
   this.getAllergens();
   this.activate = false;

   this.registerForm2 = this.formBuilder.group({
    name: ['', Validators.required],
    surname: ['', Validators.required],
    fileAvatar: [''],
    allergen: [''],
    });
  }

  onSubmit() {
    this.userService.setFinalData(this.registerForm2.value);
    this.setUser();
    delete this.userService.getFinalData()['confPassword'];
    delete this.userService.getFinalData()['fileAvatar'];

    this.authenticationService.register(this.user)
    .subscribe(
      data => {
        console.log("User created!");
          this.authenticationService.login(this.user.username, this.user.password)
           .subscribe(
               data => {
                 if (this.selectedFile != null){
                  this.profileService.updateProfileAvatar(this.selectedFile).subscribe(
                    imagen=>{
                    },
                      (error: Error) => console.log("File uploaded!")
                   );
                 }
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

  onSwitch() {
    if (this.activate === false) {
      this.activate = true;
    }
    else if (this.activate === true) {
      this.activate = false;
    }

  }

  getAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => {
        this.allergens = allergens as Allergen[];
        });
      }


   setUser () {
     this.user["username"] = this.userService.getFinalData()['username'];
     this.user['email'] = this.userService.getFinalData()['email'];
     this.user['password'] = this.userService.getFinalData()['password'];
     this.user['name'] = this.userService.getFinalData()['name'];
     this.user['surname'] = this.userService.getFinalData()['surname'];
     this.user['allergens'] = this.userService.getFinalData()['allergen'];
   }

  onFileChanged(event) {
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile);
  }
}
