import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { User } from 'src/app/Interfaces/user.model';
import { RecipesService } from 'src/app/services/recipes.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'popup-followers',
  templateUrl: './followers.component.html',
  styleUrls: ['./followers.component.css']
})
export class FollowersComponent implements OnInit {

  @Input()
  followers_users: User[];

  avatars: any[] = [];

  @ViewChild('closebutton') closebutton: ElementRef;

  constructor(private recipesService: RecipesService, private domSanitizer: DomSanitizer, public authService: AuthenticationService,
    private router: Router) {
      this.router.routeReuseStrategy.shouldReuseRoute = function () {
        return false;
      };
    }

  ngOnInit() {
  }

  ngOnChanges(){
    if(this.followers_users.length > 0){
      this.get_followers_images();
    }
  }

  get_followers_images(){
    this.followers_users.forEach(user => {
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
