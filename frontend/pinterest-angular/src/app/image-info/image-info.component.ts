import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PictureService } from '../service/picture.service';
import { environment } from 'src/enviroments/environment';
import { PictureTagService } from '../service/picture-tag.service';
import { Tag } from '../models/tag';

//Duy
// import { ReactService } from '../service/react.service';
//Duy

@Component({
  selector: 'app-image-info',
  templateUrl: './image-info.component.html',
  styleUrls: ['./image-info.component.scss']
})
export class ImageInfoComponent {
  pictureId!: number;
  pictureInfo: any;
  tags: string[] = [];

  //Duy
  likes: any[] = [];      // Danh sách người đã like
  comments: any[] = [];   // Danh sách comment
  newComment: string = ''; // Nội dung comment mới
  userId = 1;             // Giả định user_id hiện tại là 1
  hasLiked = false;       // Trạng thái like của người dùng
  //Duy

  constructor(
    private route: ActivatedRoute,
    private pictureService: PictureService,
    private pictureTagService: PictureTagService

    //Duy
    // private reactService: ReactService
    //Duy
  ) {}

  ngOnInit(): void {
  
    this.pictureId = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Picture ID:', this.pictureId);

    
    if (this.pictureId) {
      this.pictureService.getPictureById(this.pictureId).subscribe({
        next: (data) => {
          this.pictureInfo = data;
          this.pictureInfo.url = `${environment.apiBaseUrl}/pictures/url/${this.pictureInfo.imageUrl}`;
          console.log('Picture Info:', this.pictureInfo);
        },
        
        error: (err) => {
          console.error('Error fetching picture info:', err);
        }
      });
    }

    this.pictureTagService.getTagsByPictureId(this.pictureId).subscribe({
      next: (data) => {
        this.tags = data;  // Store the fetched tags
        console.log(this.tags); // Log to ensure tags are fetched
      },
      error: (error) => {
        console.error('Error fetching tags:', error);
      },
      complete: () => {
        console.log('Tag fetch completed');
      }
    });
    
  }
}
