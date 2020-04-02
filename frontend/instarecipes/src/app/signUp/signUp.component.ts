import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormGroupDirective } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { UserService } from '../services/user.service';
import { first } from 'rxjs/operators';
import { AlertService } from '../services/alert.service';
import { MustMatch } from '../helpers/password-match';
import { STEPS } from '../constants/multi-step-form';

@Component({
  selector: 'app-signup',
  templateUrl: './signUp.component.html',
  styleUrls: ['./signUp.component.css']
})
export class SignUpComponent implements OnInit{
  @Input() formContent: any;

  @Output() readonly formSubmit: EventEmitter<any> = new EventEmitter<any>();


  registerForm: FormGroup;
  next = false;
  submitted = false;
  nextClicked = false;

  activeStepIndex: number;
  currentFormContent: Array<any>;
  formData: any;
  formFields: Array<Array<string>>;
  masterFormFields: Array<string>;
  stepItems: Array<any>;
  masterForm: Array<FormGroup>;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private authenticationService: AuthenticationService,
      private userService: UserService,
      private alertService: AlertService,
      private readonly _formBuilder: FormBuilder
  ) {
      // redirect to home if already logged in
   //   if (this.authenticationService.user) {
    //      this.router.navigate(['/']);
    //  }
  }

  ngOnInit() {
    this.activeStepIndex = 0;
    this.masterForm = [];
    this.currentFormContent = [];
    this.formFields = [];
    this.stepItems = this.formContent;


    this.stepItems.forEach((data, i) => {
      this.currentFormContent.push(this.stepItems[i]['data']); // holds name, validators, placeholder of all steps
      this.formFields.push(Object.keys(this.currentFormContent[i])); // holds string values for each field of all steps
      this.masterForm.push(this.buildForm(this.currentFormContent[i])); // holds all form groups
    });

    this.registerForm = this.formBuilder.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
        confPassword: ['', Validators.required]
    }, {
        validator: MustMatch('password', 'confPassword'),
    });


  }

  // build separate FormGroups for each form
  buildForm(currentFormContent: any): FormGroup {
    const formDetails = Object.keys(currentFormContent).reduce(
      (obj, key) => {
        obj[key] = ['', this.getValidators(currentFormContent[key])];

        return obj;
      },
      {}
    );

    return this._formBuilder.group(formDetails);
  }

 // get validator(s) for each field, if any
 getValidators(formField: any): Validators {
    const fieldValidators = Object.keys(formField.validations).map(validator => {
      if (validator === 'required') {
        return Validators[validator];
      } else {
        return Validators[validator](formField.validations[validator]);
      }
    });

    return fieldValidators;
  }

  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  onSubmit() {
    if(this.nextClicked){
      console.log("hola")
    }
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

  goToStep(step: string): void {
    console.log("my step is: ", step);
    this.activeStepIndex =
      step === 'prev' ? this.activeStepIndex - 1 : this.activeStepIndex + 1;
    this.setFormPreview();
    console.log("my step is: ", step);
    }


  setFormPreview(): void {
    this.formData = this.masterForm.reduce(
      (masterForm, currentForm) => ({ ...masterForm, ...currentForm.value }),
      {}
    );

    this.masterFormFields = Object.keys(this.formData);
  }

  onFormSubmit(): void {
    this.formSubmit.emit(this.formData);
  }

  trackByFn(index: number): number {
    return index;
  }
}
