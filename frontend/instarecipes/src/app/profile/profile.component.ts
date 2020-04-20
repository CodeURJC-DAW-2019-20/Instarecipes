import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { ProfileService } from '../services/profile.service';
import { User } from '../Interfaces/user.model';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthenticationService } from '../services/authentication.service';
import { Recipe } from '../Interfaces/recipe.model';
import { ActivatedRoute, Router } from '@angular/router';

const urls = [
  'assets/js/main2.js',
  'assets/js/admin_profile.js'
]

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {

  followers_users: User[] = [];
  following_users: User[] = [];
  avatar: any = null;
  background: any = null;
  user: User = null;
  user_recipes: any[] = [];
  id_user: number;
  n_likes: number = 0;
  loadAPI: any;

  id_script: string = "id_script";

  infoLoaded: number = 0; //this one i need to load the 6 methods (info, avatar, background, likes, following and followers)

  constructor(private profileService: ProfileService, private domSanitizer: DomSanitizer,
                public authService: AuthenticationService, private userService: UserService,
                  private router: ActivatedRoute, private route: Router) {
      this.route.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
      };
  }

  ngOnInit(){
    this.infoLoaded = 0;
    console.log("ID: " + this.router.snapshot.paramMap.get('id'));
    if(!this.router.snapshot.paramMap.get('id')){
      this.id_user = this.authService.user.id;
    }else{
      this.id_user = +(this.router.snapshot.paramMap.get('id'));
    }
    this.refresh();
  }

  check_js_aviability(){
    if(this.infoLoaded==6){
      urls.forEach(element => {
        this.loadAPI = new Promise(resolve => {
          console.log("resolving promise...");
          this.loadScript(element);
        });
      });
    }
  }

  public loadScript(url: any) {
    console.log("loading script...");
    let prev_node = document.getElementById(url);
    if(prev_node != null){
      prev_node.parentNode.removeChild(prev_node);
    }
    let node = document.createElement("script");
    node.src = url;
    node.id = url;
    node.type = "text/javascript";
    node.async = true;
    node.charset = "utf-8";
    document.getElementsByTagName("head")[0].appendChild(node);
  }

  refresh(){
    this.get_user_info();
    this.get_avatar();
    this.get_background();
    this.get_publications();
    this.get_followers_and_following();
    this.get_total_likes();
  }

  get_user_info(){
    this.profileService.getUser(this.id_user).subscribe(
      user => {
        this.user = user as User;
        this.infoLoaded++;
        this.check_js_aviability();
      }
    );
  }

  get_avatar() {
    this.profileService.getProfileAvatar(this.id_user).subscribe(
      avatar => {
        var urlCreator = window.URL;
        this.avatar = this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(avatar));
        this.infoLoaded++;
        this.check_js_aviability();
      }
    );
  }

  get_background() {
    this.profileService.getProfileBackground(this.id_user).subscribe(
      background => {
        var urlCreator = window.URL;
        this.background = this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(background));
        this.infoLoaded++;
        this.check_js_aviability();
      }
    );
  }

  get_publications(){
    this.profileService.getUserRecipes(this.id_user).subscribe(
      recipes => {
        this.user_recipes = recipes as Recipe[];
        this.check_js_aviability();
      }
    );
  }

  get_followers_and_following(){
    this.profileService.getUserFollowing(this.id_user).subscribe(
      following => {
        this.following_users = following as User[];
        this.infoLoaded++;
        this.check_js_aviability();
      }
    );
    this.profileService.getUserFollowers(this.id_user).subscribe(
      followers => {
        this.followers_users = followers as User[];
        this.infoLoaded++;
        this.check_js_aviability();
      }
    );
  }

  get_total_likes(){
    this.userService.getAllLikes(this.id_user).subscribe(
      likes => {
        this.n_likes = likes as number;
        this.infoLoaded++;
        this.check_js_aviability();
      }
    );
  }

  receive_new_followers() {
    this.userService.unfollowUser(this.id_user).subscribe(
      users =>{
        this.get_followers_and_following();
      }
    );
  }

  receive_new_following() {
    this.userService.followUser(this.id_user).subscribe(
      users =>{
        this.get_followers_and_following();
      }
    );
  }

}
