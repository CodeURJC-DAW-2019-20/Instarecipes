import { Component, OnInit, AfterViewInit, OnChanges } from '@angular/core';
import { UserService } from '../services/user.service';
import { ProfileService } from '../services/profile.service';
import { User } from '../Interfaces/user.model';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthenticationService } from '../services/authentication.service';
import { Recipe } from '../Interfaces/recipe.model';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit, AfterViewInit{
  
  followers_users: User[] = [];
  following_users: User[] = [];
  avatar: any = null;
  user: User = null;
  user_recipes: any[] = [];
  id_user: number;
  n_likes: number = 0;

  constructor(private profileService: ProfileService, private domSanitizer: DomSanitizer,
    public authService: AuthenticationService, private userService: UserService){ }

  ngOnInit(){
    // if(!this.a){
      this.id_user = this.authService.user.id;
    // }else{
      // this.id_user = history.state.data.a;
    // }
    this.get_user_info();
    this.get_avatar();
    this.get_publications();
    this.get_followers_and_following();
    this.get_total_likes();
  }

  ngAfterViewInit(){
    import('../../assets/js/main2.js');
    import('../../assets/js/admin_profile.js');
    import('../../assets/js/statistics.js');
  }

  get_user_info(){
    this.profileService.getUser(this.id_user).subscribe(
      user => {
        this.user = user as User;
      }
    );
  }

  get_avatar() {
    this.profileService.getProfileAvatar(this.id_user).subscribe(
      avatar => {
        var urlCreator = window.URL;
        this.avatar = this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(avatar));
      }
    );
  }

  get_publications(){
    this.profileService.getUserRecipes(this.id_user).subscribe(
      recipes => {
        this.user_recipes = recipes as Recipe[];
      }
    );
  }

  get_followers_and_following(){
    this.profileService.getUserFollowing(this.id_user).subscribe(
      following => this.following_users = following as User[]
    );
    this.profileService.getUserFollowers(this.id_user).subscribe(
      followers => this.followers_users = followers as User[]
    );
  }

  get_total_likes(){
    this.userService.getAllLikes(this.id_user).subscribe(
      likes => {
        this.n_likes = likes as number;
      }
    );
  }

}