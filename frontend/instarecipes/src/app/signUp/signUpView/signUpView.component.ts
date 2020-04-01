import { OnInit, Component } from '@angular/core'
import { STEPS } from '../../constants/multi-step-form';

@Component({
  selector: 'app-signUpView',
  templateUrl: './signUpView.component.html'
})

export class signUpView implements OnInit {
  formContent: any;
  formData: any;
  activeStepIndex: number;

  ngOnInit(): void {
    this.formContent = STEPS;
    this.formData = {};
  }

  onFormSubmit(formData: any): void {
    this.formData = formData;

    // post form data here
    alert(JSON.stringify(this.formData));
  }
}
