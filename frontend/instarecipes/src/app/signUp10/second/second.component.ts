import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { ProfileService } from 'src/app/services/profile.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { User } from 'src/app/Interfaces/user.model';

@Component({
  selector: 'app-second',
  templateUrl: './second.component.html',
})
export class SecondComponent implements OnInit {
  registerForm2: FormGroup;
  activate: boolean;
  allergens: Allergen [];
  returnUrl: string;
  error: '';
  user: User;

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
    console.log("final data " , this.userService.getFinalData());
    this.setUser();
    delete this.userService.getFinalData()['confPassword'];
    delete this.userService.getFinalData()['fileAvatar'];

    console.log("nuevo ", this.userService.getFinalData());

    this.authenticationService.register(this.user)
    .pipe(first())
    .subscribe(
      data => {
          alert("User created!");
          this.router.navigate(['/login']);
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
     console.log("Im in setuser()");
     this.user["username"] = this.userService.getFinalData()['username'];
     this.user['email'] = this.userService.getFinalData()['email'];
     this.user['password'] = this.userService.getFinalData()['password'];
     this.user['name'] = this.userService.getFinalData()['name'];
     this.user['surname'] = this.userService.getFinalData()['surname'];
     this.user['allergens'] = this.userService.getFinalData()['allergen'];

     console.log("set user ", this.user);

   }

}
