import { Component, OnInit, Input } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Allergen } from 'src/app/Interfaces/allergen.model';
import { ProfileService } from 'src/app/services/profile.service';

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
  allAllergens: Allergen[] = [];


  allergenAux: string = '';

  constructor(private profileService: ProfileService, public authService: AuthenticationService) { }

  ngOnInit() {
    import('../../../../../assets/js/image_preview.js')
    
    this.loadAllergens();
  }

  loadAllergens(){
    this.profileService.getAllAllergens().subscribe(
      allergens => this.allAllergens = allergens
    )
  }

}
