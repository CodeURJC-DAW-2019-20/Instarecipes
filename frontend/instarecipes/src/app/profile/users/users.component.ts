import { Component, OnInit } from '@angular/core';

import { User } from '../../Interfaces/user.model';

@Component({
  selector: 'user-info',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  user: User;

  constructor() { }

  ngOnInit() {
    
  }

}
