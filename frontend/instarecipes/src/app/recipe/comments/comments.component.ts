import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'recipe-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  comments: Comment[] = [];
  avatar: any[] = [];
  constructor() {}

  ngOnInit() {
  }

}
