import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MustMatch } from '../../helpers/password-match';
import { SecondComponent } from '../second/second.component';
import { UserService } from 'src/app/services/user.service';
import { BehaviorSubject } from 'rxjs';


@Component({templateUrl: 'first.component.html'})
export class FirstComponent implements OnInit {
    registerForm: FormGroup;
    submitted = false;
    returnUrl: string;
    userService: UserService;

    constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      ) { }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            username: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', Validators.required],
            confPassword: ['', Validators.required]
        }, {
            validator: MustMatch('password', 'confPassword')
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
        else {
            this.router.navigate(['/second']);
            alert(JSON.stringify(this.registerForm.value));
        }

    }
}
