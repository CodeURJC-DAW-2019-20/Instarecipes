import { Component, OnInit } from '@angular/core';
import { EditProfileComponent } from '../popup/editProfile/editProfile.component';
//import { MatDialog, MatDialogConfig } from "@angular/material";

@Component({
  selector: 'user-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  editProfile(){
    //this.dialog.open(EditProfileComponent);
  }
}
