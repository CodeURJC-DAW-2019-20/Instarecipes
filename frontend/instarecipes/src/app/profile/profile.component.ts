import { Component, OnInit, AfterViewInit, OnChanges } from '@angular/core';
import { UserService } from '../services/user.service';
import { ProfileService } from '../services/profile.service';
import { User } from '../Interfaces/user.model';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthenticationService } from '../services/authentication.service';
import { Recipe } from '../Interfaces/recipe.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit, AfterViewInit{

  followers_users: User[] = [];
  following_users: User[] = [];
  avatar: any = null;
  background: any = null;
  user: User = null;
  user_recipes: any[] = [];
  id_user: number;
  n_likes: number = 0;

  infoLoaded: number = 0; //this one i need to load the 6 methods (info, avatar, background, likes, following and followers)

  constructor(private profileService: ProfileService, private domSanitizer: DomSanitizer,
                public authService: AuthenticationService, private userService: UserService,
                  private router: ActivatedRoute, private route: Router) {
      this.route.routeReuseStrategy.shouldReuseRoute = function () {
        return false;
      };
  }

  ngOnInit(){
    console.log("ID: " + this.router.snapshot.paramMap.get('id'));
    if(!this.router.snapshot.paramMap.get('id')){
      this.id_user = this.authService.user.id;
      console.log("Welcome to your profile");
    }else{
      this.id_user = +(this.router.snapshot.paramMap.get('id'));
      console.log("Welcome to jurasic park");
    }
    this.refresh();
  }

  refresh(){
    this.followers_users = [];
    this.following_users = [];
    this.avatar = null;
    this.background = null;
    this.user = null;
    this.user_recipes = [];
    this.n_likes = 0;

    this.get_user_info();
    this.get_avatar();
    this.get_background();
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
        this.infoLoaded++;
      }
    );
  }

  get_avatar() {
    this.profileService.getProfileAvatar(this.id_user).subscribe(
      avatar => {
        var urlCreator = window.URL;
        this.avatar = this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(avatar));
        this.infoLoaded++;
      }
    );
  }

  get_background() {
    this.profileService.getProfileBackground(this.id_user).subscribe(
      background => {
        var urlCreator = window.URL;
        this.background = this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(background));
        this.infoLoaded++;
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
      following => {
        this.following_users = following as User[];
        this.infoLoaded++;
      }
    );
    this.profileService.getUserFollowers(this.id_user).subscribe(
      followers => {
        this.followers_users = followers as User[];
        this.infoLoaded++;
      }
    );
  }

  get_total_likes(){
    this.userService.getAllLikes(this.id_user).subscribe(
      likes => {
        this.n_likes = likes as number;
        this.infoLoaded++;
      }
    );
  }

  receive_new_followers() {
    this.get_followers_and_following();
  }

  receive_new_following() {
    this.get_followers_and_following();
  }

}
