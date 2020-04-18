import { Component, OnInit, Input } from '@angular/core';

import { User } from '../../Interfaces/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Recipe } from 'src/app/Interfaces/recipe.model';

@Component({
  selector: 'user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: User;

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
  
/*   a = history.state.data.a;
 */  constructor(public authService: AuthenticationService) { }

  ngOnInit() {
/*     console.log('heeeeeeeello ', this.a);
 */  }

}
