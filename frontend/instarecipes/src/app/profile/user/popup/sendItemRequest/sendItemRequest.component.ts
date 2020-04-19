import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {Request} from '../../../../Interfaces/request.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProfileService } from 'src/app/services/profile.service';
import { RecipesService } from 'src/app/services/recipes.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'popup-send-item-request',
  templateUrl: './sendItemRequest.component.html',
  styleUrls: ['./sendItemRequest.component.css']
})
export class SendItemRequestComponent implements OnInit {
  registerForm: FormGroup;
  request_:Request=null;
  type: string='';
  content: String='';

  @ViewChild('closebutton') closebutton: ElementRef;

  constructor (private profileService: ProfileService,
    public authService: AuthenticationService,private formBuilder: FormBuilder,) {
      this.initConstructor();
  }
  initConstructor(){
    this.request_ = {typeOfRequest: '', ingredientContent: '', cookingStyleContent: '',
    categoryContent: ''};

  }


  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      content: ['', Validators.required],
  });

  }


  sendRequest(){
    console.log("tipo de request ",this.type);
    //this.request_.username = this.authService.user;
    this.typeClick();
    console.log("I made this request ", this.request_);
    this.profileService.getRequest(this.request_).subscribe(
       response => {
         console.log("request hecha");
       }
    );

    this.closebutton.nativeElement.click();
  }


  typeClick(){
    switch (this.request_.typeOfRequest) {
      case "Ingredient":
        this.request_.typeOfRequest = "Ingredient";
        this.request_.ingredientContent=this.type
        break;
      case "Cooking Style":
        this.request_.typeOfRequest = "Cooking Style";
        this.request_.cookingStyleContent=this.type
        break;
      case "Category":
        this.request_.typeOfRequest = "Category";
        this.request_.categoryContent=this.type

        break;
      default:
        this.request_.typeOfRequest = "";
        break;
    }
  }

}
