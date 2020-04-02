import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-second',
  templateUrl: './second.component.html',
})
export class SecondComponent implements OnInit {
  registerForm: FormGroup;
  activate: boolean;
  userService: UserService;

  constructor(
    private formBuilder: FormBuilder
    ) { }

  ngOnInit() {
   this.activate = false;
   console.log(this.activate);

   this.registerForm = this.formBuilder.group({
    name: ['', Validators.required],
    surname: ['', Validators.required],
    });
  }

  onSubmit() {
  }

  onSwitch() {
    if (this.activate === false) {
      this.activate = true;
    }
    else if (this.activate === true) {
      this.activate = false;
    }

  }

}
