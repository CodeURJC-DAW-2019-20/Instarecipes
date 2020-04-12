import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'admin-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }


  pushed(){
    
  }
  //Metodo que compruebe que es admin
  isAdmin: boolean=true;
  IsHidden= true;

  onSelect(){
  this.IsHidden= !this.IsHidden;
  }
}
