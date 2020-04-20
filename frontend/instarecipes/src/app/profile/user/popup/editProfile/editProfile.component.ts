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
  userUpdate;

  @ViewChild('closebutton') closebutton: ElementRef;

  allergenAux: String = '';
  name : String;
  surname: String;
  info : String;
  allergens: String;
  newAvatar: File;
  newBackground: File;

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
    if (this.name != null) {
       this.userUpdate.name = this.name;
    }
    if (this.surname != null) {
      this.userUpdate.surname = this.surname;
    }
    if (this.info != null) {
      this.userUpdate.info = this.info;
    }
    if (this.allergens != null) {
      this.userUpdate.allergens = this.allergens;
    }

    this.profileService.editProfile(this.userUpdate).subscribe(
    _ =>{
      if (this.newAvatar != null){
        this.profileService.updateProfileAvatar(this.newAvatar).subscribe(
          imagen=>{
          },
            (error: Error) => console.log("File uploaded!")
         );
       }
      
    });

    this.initConstructor();
    this.closebutton.nativeElement.click();
  }


  loadAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => this.allAllergens = allergens
    )
  }

  onFileChanged(event) {
    this.newAvatar = event.target.files[0];
    console.log(this.newAvatar);
  }

  onBackgroundChanged(event) {
    this.newBackground = event.target.files[0];
    console.log(this.newBackground);
  }

}
