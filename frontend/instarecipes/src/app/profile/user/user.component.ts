import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { User } from '../../Interfaces/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Recipe } from 'src/app/Interfaces/recipe.model';
import { UserService } from 'src/app/services/user.service';

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

  @Output()
  new_followers = new EventEmitter<User[]>();
  @Output()
  new_following = new EventEmitter<User[]>();
  
  constructor(public authService: AuthenticationService, private userService: UserService) { }

  ngOnInit() {
  }

  followUser(){
    this.userService.followUser(this.authService.user.id).subscribe(
      users =>{
        this.new_following.emit(users);
      }
    );
  }

  unfollowUser(){
    this.userService.unfollowUser(this.authService.user.id).subscribe(
      users =>{
        this.new_followers.emit(users);
      }
    );
  }

}
