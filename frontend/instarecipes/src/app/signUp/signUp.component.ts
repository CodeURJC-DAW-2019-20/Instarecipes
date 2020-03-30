import {Component, OnInit} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { UserService } from '../services/user.service';
import { first } from 'rxjs/operators';
import { AlertService } from '../services/alert.service';
import { MustMatch } from '../helpers/password-match';

@Component({
  selector: 'app-signup',
  templateUrl: './signUp.component.html',
  styleUrls: ['./signUp.component.css']
})
export class SignUpComponent implements OnInit{

  registerForm: FormGroup;
  next = false;
  submitted = false;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private authenticationService: AuthenticationService,
      private userService: UserService,
      private alertService: AlertService
  ) {
      // redirect to home if already logged in
   //   if (this.authenticationService.user) {
    //      this.router.navigate(['/']);
    //  }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
        confPassword: ['', Validators.required]
    }, {
        validator: MustMatch('password', 'confPassword'),
    });
}
  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  onSubmit() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.registerForm.invalid) {
          return;
      }

      this.next = true;
      //this.userService.register(this.registerForm.value)
        //  .pipe(first())
          // .subscribe(
          //     data => {
          //         this.alertService.success('Registration successful', true);
          //         this.router.navigate(['/login']);
          //     },
          //     error => {
          //         this.alertService.error(error);
          //         this.loading = false;
          //     });
  }

}
