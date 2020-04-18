import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { User } from 'src/app/Interfaces/user.model';
import { RecipesService } from 'src/app/services/recipes.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Component({
  selector: 'popup-following',
  templateUrl: './following.component.html',
  styleUrls: ['./following.component.css']
})
export class FollowingComponent implements OnInit, OnChanges {

  @Input()
  following_users: User[];

  avatars: any[] = [];

  constructor(private recipesService: RecipesService, private domSanitizer: DomSanitizer,
    private router: Router) { }

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
    this.router.navigateByUrl('/users/'+id);
  }

}
