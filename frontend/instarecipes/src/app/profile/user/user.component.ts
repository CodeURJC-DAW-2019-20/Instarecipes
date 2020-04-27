import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';

import { User } from '../../Interfaces/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  @Input()
  avatar: any;
  @Input()
  background: any;
  @Input()
  following_users: User[];
  @Input()
  followers_users: User[];
  @Input()
  user_recipes: Recipe[];
  @Input()
  n_likes: number;
  @Input()
  user: User;
  @Input()
  infoLoaded: number;
  @Input()
  id_user: number;

  user_list_aux: User[] = [];

  @Output()
  new_followers = new EventEmitter<any>();
  @Output()
  new_following = new EventEmitter<any>();
  @Output()
  refresh_profile = new EventEmitter<any>();

  followed_user: boolean = false;
  
  constructor(
    public authService: AuthenticationService,
    private profileService: ProfileService
  ) { }

  ngOnInit(){
    this.loadFollowingList();
  }

  loadFollowingList(){
    this.profileService.getUserFollowing(this.authService.user.id).subscribe(
      following => {
        this.user_list_aux = following as User[];
        this.user_list_aux.forEach(element => {
          if(this.user.id === element.id){
            this.followed_user = true;
          }
        });
      }
    );
  }

  followUser(){
    this.followed_user = true;
    this.new_following.emit(null);
  }

  unfollowUser(){
    this.followed_user = false;
    this.new_followers.emit(null);
  }

  update_profile(){
    this.refresh_profile.emit(null);
  }

}
