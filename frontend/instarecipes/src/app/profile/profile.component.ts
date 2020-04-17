import { Component, OnInit, AfterViewInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { ProfileService } from '../services/profile.service';
import { Router } from '@angular/router';
import { User } from '../Interfaces/user.model';
import { DomSanitizer } from '@angular/platform-browser';



@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit,AfterViewInit{
  users: User[] = []
  avatar: any[] = [];
  user: User;
  recipes: any[]=[];
  href: string = "";
  parts: string[]=[];
  id_user: number=-1;
  constructor(private profileService: ProfileService, 
    private domSanitizer: DomSanitizer,private router: Router){

  }

  ngOnInit(){
    this.href = this.router.url;
    this.parts=this.href.split('/');
    this.id_user=Number(this.parts[-1]);
    this.refresh(this.id_user);



  }

  ngAfterViewInit(){
    import ('../../assets/js/main2.js')

  }

  refresh(id_user : number){
    this.profileService.getUser(id_user).subscribe(
      user => {
        this.user = user;
        this.userProfileAvatar(user);
        this.get_Recipes(id_user);      }
    );
  }
  get_Recipes(id_user: number){
    this.profileService.getProfileAvatar(id_user).subscribe(
      data => {
        var urlCreator = window.URL;
        this.recipes.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }
  userProfileAvatar(u: User) {
    let id_user = u.id;
    this.profileService.getProfileAvatar(id_user).subscribe(
      data => {
        var urlCreator = window.URL;
        this.avatar.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(data)));
      }
    );
  }

 


}