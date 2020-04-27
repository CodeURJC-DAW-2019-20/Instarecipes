import { Component, OnInit, Input, ViewChild, ElementRef, Output, EventEmitter, OnChanges } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { ProfileService } from 'src/app/services/profile.service';
import { User } from '../../../../Interfaces/user.model';
import { FormGroup} from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'popup-edit-profile',
  templateUrl: './editProfile.component.html',
  styleUrls: ['./editProfile.component.css']
})
export class EditProfileComponent implements OnInit, OnChanges {

  @Input()
  avatar: any;
  @Input()
  background: any;
  @Input()
  user: User;
  @Input()
  id_user: number;

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

  realBackground: any;
  cont_aux: number;

  @ViewChild('closebutton') closebutton: ElementRef;

  constructor(
    private profileService: ProfileService,
    public authService: AuthenticationService,
    private domSanitizer: DomSanitizer
  ) {
    this.initConstructor();
  }

  initConstructor(){
    this.userUpdate = {
      name: this.authService.user.name,
      surname: this.authService.user.surname,
      info: this.authService.user.info,
      allergens: this.authService.user.allergens
    }
    this.newBackground = null;
    this.newAvatar = null;
    this.cont_aux = 0;
  }

  ngOnInit() {
    if(this.id_user === this.authService.user.id){
      this.loadAPI = new Promise(resolve => {
        console.log("resolving promise...");
        this.loadScript();
      });
      this.loadAllergens();
    }
    this.realBackground = this.domSanitizer.bypassSecurityTrustUrl(this.background);
  }

  ngOnChanges(){
    this.realBackground = this.domSanitizer.bypassSecurityTrustUrl(this.background);
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
    this.profileService.editProfile(this.userUpdate).subscribe(
      _ => {
        this.user = _ as User;
        this.cont_aux++;
        if (this.newBackground != null){
          this.profileService.updateProfileBackground(this.newBackground).subscribe(
            __ => { },
            error => {
              console.log("Background file uploaded!");
              this.cont_aux++;
              this.checkChanges();
            }
          );
        }else{
          this.cont_aux++;
          this.checkChanges();
        }
        if (this.newAvatar != null){
          this.profileService.updateProfileAvatar(this.newAvatar).subscribe(
            __ => { },
            error => {
              console.log("Avatar file uploaded!");
              this.cont_aux++;
              this.checkChanges();
            }
          );
        }else{
          this.cont_aux++;
          this.checkChanges();
        }
    },
    error => alert("Try again"));
  }

  checkChanges(){
    if(this.cont_aux == 3){
      this.update_profile();
      this.initConstructor();
      this.closebutton.nativeElement.click();
    }
  }

  loadAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => this.allAllergens = allergens
    )
  }

  update_profile(){
    console.log('me ejecuto');
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
