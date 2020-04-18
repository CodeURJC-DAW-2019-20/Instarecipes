import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'requests',
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
  isHidden= true;

  onSelect(){
  this.isHidden= !this.isHidden;
  }
}
