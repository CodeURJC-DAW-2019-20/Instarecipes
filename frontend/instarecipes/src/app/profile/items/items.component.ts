import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'items',
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
