import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ProfileService } from '../../services/profile.service';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { User } from '../../Interfaces/user.model';
import { isNumber } from 'util';

@Component({
  selector: 'user-info',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: User[] = []
  avatar: any[] = [];
  user: User;
  href: string = "";
  parts: string[]=[];
  user_actual: User;
  id_user: number=-1;
  constructor(private profileService: ProfileService, 
    private domSanitizer: DomSanitizer,private router: Router){

  }

  ngOnInit(){
    this.href = this.router.url;
    this.parts=this.href.split('/');
    if(isNumber( this.parts[-1])){
      this.id_user=Number(this.parts[-1]);
      this.refresh(this.id_user);
    }
    else{
      this.refresh1();

    }
    
  }

  refresh(id_user : number){
    this.profileService.getUser(id_user).subscribe(
      user => {
        this.user = user;
        this.userProfileAvatar(id_user);    }
    );
  }
  
  refresh1(){
    this.profileService.getActualUser().subscribe(
      user => {
        this.user = user;
        this.userProfileAvatar(user);
      }
    )
  }

  userProfileAvatar(u: number) {
    
    this.profileService.getProfileAvatar(u).subscribe(
      data => {
        var urlCreator = window.URL;
        this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }
  

}
