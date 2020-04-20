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

  acceptRequest(request: Request) {
    let itemContent = request.ingredientContent + request.categoryContent + request.cookingStyleContent;
  this.profileService.actionItemRequest(request.typeOfRequest, itemContent, "accept", request.id).subscribe(
    response => {
      console.log("Petition accepted");
      this.getRequests();
    });
  }

  denyRequest(request: Request) {
    let itemContent = request.ingredientContent + request.categoryContent + request.cookingStyleContent;
    this.profileService.actionItemRequest(request.typeOfRequest, itemContent, "decline", request.id).subscribe(
      response => {
        console.log("Petition denied");
        this.getRequests();
      });
    }
}
