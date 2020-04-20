import { Component, OnInit, Input, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { ProfileService } from 'src/app/services/profile.service';
import { User } from '../../../../Interfaces/user.model';
import { FormGroup} from '@angular/forms';


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

  @Output()
  refresh_profile = new EventEmitter<any>();

  allAllergens: Allergen[] = [];
  userUpdate: any;
  allergenAux: string = '';
  settingsForm : FormGroup;
  name: string;
  surname: string;
  info: string;
  allergens: string;
  loadAPI: any;
  newAvatar: File;
  newBackground: File;

  @ViewChild('closebutton') closebutton: ElementRef;

  constructor(private profileService: ProfileService, public authService: AuthenticationService) {   
    this.userUpdate = { 
      name: authService.user.name,
      surname: authService.user.surname,
      info: authService.user.info,
      allergens: authService.user.allergens
    }
  }  

  ngOnInit() {
    this.loadAPI = new Promise(resolve => {
      console.log("resolving promise...");
      this.loadScript();
    });
    this.loadAllergens();  
  }

  public loadScript() {
    console.log("preparing to load...");
    let node = document.createElement("script");
    node.src = 'assets/js/image_preview.js';
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
  }
  
  initConstructor(){
    //no se si inicializarlo con user o authservice, en teoria debería ser con auth pero si cambiamos algo no se cambia en el authservice,
    //probar a cambiar nombre e ir a la consola en el index! si te log con pepe sigue saliendo de nombre pepe (al igual que en el popup) pero en el profile cambia.
    //this.userUpdate = { name: this.user?.name, surname: this.user?.surname, info: this.user?.info, allergens: this.user?.allergens }
    //this.userUpdate = { name: '', surname: '', info: '', allergens: '' }

    this.userUpdate = { name: this.user?.name, surname: this.authService.user.surname, info: this.authService.user.info, allergens: this.authService.user.allergens }
  }

  editProfile(){
    console.log(this.userUpdate);
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
    console.log("after ", this.userUpdate);
    this.profileService.editProfile(this.userUpdate).subscribe(
    _ =>{
      this.user = _ as User;
      console.log(_);
      console.log(this.user);
      if (this.newAvatar != null){
        this.profileService.updateProfileAvatar(this.newAvatar).subscribe(
          imagen=>{ },
          (error: Error) => console.log("File uploaded!")
         );
       }
    });
    this.closebutton.nativeElement.click();
  }


  loadAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => this.allAllergens = allergens
    )
  }

  update_profile(){
    this.refresh_profile.emit(null);
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
