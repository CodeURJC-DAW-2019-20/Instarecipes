import { Component, OnInit, Input, OnChanges, ViewChild, ElementRef } from '@angular/core';
import { User } from 'src/app/Interfaces/user.model';
import { RecipesService } from 'src/app/services/recipes.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'popup-following',
  templateUrl: './following.component.html',
  styleUrls: ['./following.component.css']
})
export class FollowingComponent implements OnInit, OnChanges {

  @Input()
  following_users: User[];

  avatars: any[] = [];

  @ViewChild('closebutton') closebutton: ElementRef;

  constructor(
    private recipesService: RecipesService,
    private domSanitizer: DomSanitizer,
    private router: Router,
    public authService: AuthenticationService) {
      this.router.routeReuseStrategy.shouldReuseRoute = function () {
        return false;
      };
    }

  ngOnInit() {
  }

  ngOnChanges(){
    if(this.following_users.length > 0){
      this.get_following_images();
    }
  }

  get_following_images(){
    this.following_users.forEach(user => {
      this.recipesService.getRecipeAvatar(user.id).subscribe(
        data => {
          var urlCreator = window.URL;
          this.avatars.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
        }
      );
    });
  }

  visitProfile(id: number){
    if(this.authService.user.id === id){
      this.router.navigateByUrl('/profile');
    }else{
      this.router.navigateByUrl('/users/'+id);
    }
    this.closebutton.nativeElement.click();
  }

}
