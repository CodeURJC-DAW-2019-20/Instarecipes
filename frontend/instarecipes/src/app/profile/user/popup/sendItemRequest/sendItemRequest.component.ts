import { Component, OnInit } from '@angular/core';
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
  
  content: String='';
  constructor (private profileService: ProfileService,
    public authService: AuthenticationService,private formBuilder: FormBuilder,) {
      this.initConstructor();
  }
  initConstructor(){
    this.request_ = { username: null, typeOfRequest: '', ingredientContent: '', cookingStyleContent: '',
    categoryContent: '', itemExists: false, 
    };
    
  }


  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      content: ['', Validators.required],
  });

  }


  sendRequest(){
    console.log(this.registerForm.value.content)
    this.request_.username = this.authService.user;

    if(this.registerForm.invalid){
      return;
    }
    else{
      this.typeClick();
      this.profileService.getRequest(this.request_);
    }
  }


  typeClick(){
    switch (this.request_.typeOfRequest) {
      case "Ingredient":
        this.request_.typeOfRequest = "Ingredient";
        this.request_.ingredientContent=this.registerForm.value.content
        break;
      case "Cooking Style":
        this.request_.typeOfRequest = "Cooking Style";
        this.request_.cookingStyleContent=this.registerForm.value.content
        break;
      case "Category":
        this.request_.typeOfRequest = "Category";
        this.request_.categoryContent=this.registerForm.value.content

        break;
      default:
        this.request_.typeOfRequest = "";
        break;
    }
  }

}
