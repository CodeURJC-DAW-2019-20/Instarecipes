import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-signUp10',
  templateUrl: './signUp10.component.html',
  styleUrls: ['./signUp10.component.css']
})
export class SignUp10Component implements OnInit {
  title = 'Multi-Step Wizard';
  @Input() formData;

  constructor() {
  }

  ngOnInit() {
      this.formData = this.formData.getFormData();
      console.log(this.title + ' loaded!');
  }
}
