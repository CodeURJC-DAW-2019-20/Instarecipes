import { Component, OnInit, ViewChild, ElementRef, OnChanges } from '@angular/core';
import {Request} from '../../../../Interfaces/request.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'popup-send-item-request',
  templateUrl: './sendItemRequest.component.html',
  styleUrls: ['./sendItemRequest.component.css']
})
export class SendItemRequestComponent implements OnInit, OnChanges {

  request: Request = null;
  type: string;
  content: string;

  @ViewChild('closebutton') closebutton: ElementRef;

  constructor (private profileService: ProfileService, public authService: AuthenticationService) {
      this.initConstructor();
  }

  initConstructor(){
    this.type = '';
    this.content = '';
    this.request = {typeOfRequest: '', ingredientContent: '', cookingStyleContent: '', categoryContent: ''};
  }

  ngOnInit() {
  }

  ngOnChanges(){
    console.log(this.type);
    console.log(this.content);
  }

  sendRequest(){
    this.typeClick();
    if(this.type !== "" && this.content !== ""){
      this.profileService.getRequest(this.request).subscribe(
        response => {
          console.log("request done");
          this.initConstructor();
        }
     );
     this.closebutton.nativeElement.click(); 
    }
  }

  typeClick(){
    switch (this.type) {
      case "Ingredient":
        this.request.typeOfRequest = "Ingredient";
        this.request.ingredientContent = this.content;
        break;
      case "Cooking style":
        this.request.typeOfRequest = "Cooking style";
        this.request.cookingStyleContent=this.content;
        break;
      case "Category":
        this.request.typeOfRequest = "Category";
        this.request.categoryContent = this.content;
        break;
      default:
        this.request.typeOfRequest = "";
        break;
    }
  }

}
