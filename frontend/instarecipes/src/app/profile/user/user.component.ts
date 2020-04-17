import { Component, OnInit } from '@angular/core';

import { User } from '../../Interfaces/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: User;
/*   a = history.state.data.a;
 */  constructor(public authService: AuthenticationService) { }

  ngOnInit() {
/*     console.log('heeeeeeeello ', this.a);
 */  }

}
