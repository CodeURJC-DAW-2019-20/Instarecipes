import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit() {

  }

  onSubmit() {
      console.error('LOG OUT');
      this.authenticationService.logout().subscribe(
        data => {
          console.error(data.toString());
          location.assign('');
        }
        );
    }
}
