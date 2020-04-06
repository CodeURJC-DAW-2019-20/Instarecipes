import {Component,OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../Interfaces/user.model';
import { DomSanitizer } from '@angular/platform-browser';



@Component({
  selector: 'app-home',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent{
  users: User[] = []
  user: User;
  constructor(private usersService: UserService, 
    private domSanitizer: DomSanitizer){

  }

  ngOnInit(){
    this.refresh();
  }

  refresh(){
    
  }

 


}