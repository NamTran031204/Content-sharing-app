import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent implements OnInit {
  
  images = [
    { url: 'https://via.placeholder.com/400', title: 'Image 1' },
    { url: 'https://via.placeholder.com/400', title: 'Image 2' },
    { url: 'https://via.placeholder.com/400', title: 'Image 3' },
    { url: 'https://via.placeholder.com/400', title: 'Image 4' },
    { url: 'https://via.placeholder.com/400', title: 'Image 5' },
  ];

  constructor() {}

  ngOnInit(): void {}
}