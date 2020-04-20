import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { ProfileService } from 'src/app/services/profile.service';
import { User } from '../../../../Interfaces/user.model';
import { UserService } from '../../../../services/user.service';
import { FormBuilder, FormGroup} from '@angular/forms';


@Component({
  selector: 'popup-edit-profile',
  templateUrl: './editProfile.component.html',
  styleUrls: ['./editProfile.component.css']
})
export class EditProfileComponent implements OnInit {

  @Input()
  avatar: any;
  @Input()
  background: any;
  @Input()
  user: User;
  allAllergens: Allergen[] = [];
  settingsForm : FormGroup
  userUpdate 
  @ViewChild('closebutton') closebutton: ElementRef;

  allergenAux: string = '';
  name : String;
  surname: String;
  info : String;
  allergens: String

  constructor(private profileService: ProfileService,
     public authService: AuthenticationService, private userService: UserService) {   
       this.initConstructor();
    }  

  ngOnInit() {
    import('../../../../../assets/js/image_preview.js')
    this.loadAllergens();  
  }

  initConstructor(){
    this.userUpdate = { name: '', surname: '', info: '', allergens: '' }
  }
   
  editProfile(){
    this.userUpdate.name = this.name;
    this.userUpdate.surname = this.surname;
    this.userUpdate.info = this.info;
    this.userUpdate.allergens = this.allergens;
    console.log(JSON.stringify(this.userUpdate));
    this.profileService.editProfile(this.userUpdate).subscribe(

       _ =>{
                this.initConstructor();
                this.closebutton.nativeElement.click();  
       }
    )  
  }

  
  loadAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => this.allAllergens = allergens
    )
  }
  
}