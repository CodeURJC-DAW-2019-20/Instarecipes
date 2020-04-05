import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { ProfileService } from 'src/app/services/profile.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-second',
  templateUrl: './second.component.html',
})
export class SecondComponent implements OnInit {
  registerForm2: FormGroup;
  activate: boolean;
  allergens: Allergen [];
  allergenSelected: Allergen;
  returnUrl: string;
  error: '';

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private profileService: ProfileService,
    private router: Router,
    private authenticationService: AuthenticationService,
    ) { }

  ngOnInit() {
   this.getAllergens();
   this.activate = false;

   this.registerForm2 = this.formBuilder.group({
    name: ['', Validators.required],
    surname: ['', Validators.required],
    fileAvatar: [''],
    allergens: ['']
    });
  }

  onSubmit() {
    this.userService.setFinalData(this.registerForm2.value);
    console.log("final data " , this.userService.getFinalData());
    console.log("selected allergen: ", this.allergenSelected?.allergen);

   // this.authenticationService.login(data[1], data[1])
    //.pipe(first())
    //.subscribe(
      //  data => {
      //      this.router.navigate([this.returnUrl]);
      //  },
     //   error => {
      //      this.error = error;
      //  });
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

  saveAllergen(allergen: Allergen) {
    console.log("hola"  + allergen.allergen);
  }
}
