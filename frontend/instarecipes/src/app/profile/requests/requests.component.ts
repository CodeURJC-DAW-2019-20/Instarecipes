import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ProfileService } from 'src/app/services/profile.service';
import { Request } from 'src/app/Interfaces/request.model';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  requests: Request[] = [];
  avatars: any[] = [];
  itemExists: boolean = false;
  itemContent: string;

  @ViewChild('itemContent') itemCont: ElementRef;

  constructor(
    private profileService: ProfileService,
    private authService: AuthenticationService,
    private router: Router,
    private domSanitizer: DomSanitizer) {

    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
   }

  ngOnInit() {
    this.getRequests();
  }

  getRequests() {
    this.profileService.getRequestList().subscribe(
      request => {
         this.requests = request as Request[];
         this.requests.forEach(element =>
          this.avatarUsers(element)
         )
        console.log(request);
      }
    );
  }

  avatarUsers(request: Request) {
    this.profileService.getProfileAvatar(request.username.id).subscribe(
      avatar => {
        var urlCreator = window.URL;
        this.avatars.push(this.domSanitizer.bypassSecurityTrustUrl(urlCreator.createObjectURL(avatar)));
      }
    )
  }

  visitProfile(id: number){
    if(this.authService.user.id === id){
      this.router.navigateByUrl('/profile');
    }else{
      this.router.navigateByUrl('/users/'+id);
    }
  }

  acceptRequest(typeOfRequest: string, itemContent: string, id_request: number) {
  this.profileService.ActionItemRequest(typeOfRequest, itemContent, "accept", id_request).subscribe(
    response => {
      alert("Petition accepted");
      this.router.navigateByUrl('/profile');
    });
  }

  denyRequest(typeOfRequest: string, itemContent: string, id_request: number) {
    this.profileService.ActionItemRequest(typeOfRequest, itemContent, "decline", id_request).subscribe(
      response => {
        alert("Petition denied");
        this.router.navigateByUrl('/profile');
      });
    }
}
