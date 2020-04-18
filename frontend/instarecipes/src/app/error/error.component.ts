import {Component} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent{
  constructor(private router: Router){ }
  
}