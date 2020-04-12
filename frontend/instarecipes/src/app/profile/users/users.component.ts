import { Component, OnInit } from '@angular/core';

import { User } from '../../Interfaces/user.model';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'user-info',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  user: User;

  constructor(public authService: AuthenticationService) { }

  ngOnInit() {

  }

}
