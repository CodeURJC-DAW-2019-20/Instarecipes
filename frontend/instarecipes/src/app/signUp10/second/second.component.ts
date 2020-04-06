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
    allergen: ['']
    });
  }

  onSubmit() {
    this.userService.setFinalData(this.registerForm2.value);
    console.log("final data " , this.userService.getFinalData());
    //this.userService.setUser();
    delete this.userService.getFinalData()['confPassword'];
    delete this.userService.getFinalData()['fileAvatar'];

    console.log("nuevo ", this.userService.getFinalData());

    this.authenticationService.register(this.userService.getFinalData())
    .pipe(first())
    .subscribe(
      data => {
          alert("YES!");
          this.router.navigate(['/login']);
      },
      error => {
          alert("NO!");
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

}
