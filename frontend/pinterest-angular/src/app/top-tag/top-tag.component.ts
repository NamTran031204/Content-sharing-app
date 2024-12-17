
// import { Component, OnInit } from '@angular/core';


// @Component({
//   selector: 'app-top-tag',
//   templateUrl: './top-tag.component.html',
//   styleUrls: ['./top-tag.component.scss']
// })
// export class TopTagComponent implements OnInit{
//   topTags: any[] = [];  // To store the top tags

//   constructor(private pictureTagService: PictureTagService) { }

//   ngOnInit(): void {
//     this.getTopTags();
//   }

//   getTopTags(): void {
//     this.pictureTagService.getTopTags().subscribe({
//       next: (tags) => {
//         this.topTags = tags;
//       },
//       error: (err) => {
//         console.error('Error fetching top tags:', err);
//       }
//     });
//   }

// }
