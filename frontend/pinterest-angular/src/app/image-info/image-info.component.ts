import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PictureService } from '../service/picture.service';
import { environment } from 'src/enviroments/environment';
import { PictureTagService } from '../service/picture-tag.service';
import { Tag } from '../models/tag';
import { ReactDTO } from '../dtos/reacts/react.dto';
import { UserService } from '../service/user.service';
import { NgModel } from '@angular/forms';

//Duy
import { ReactService } from '../service/react.service';
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
  comments: any;
  userReact: any;
  newComment: string = '';
  isLiked: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private pictureService: PictureService,
    private pictureTagService: PictureTagService,
    private reactService: ReactService,
    private userService: UserService
  ) { }

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

    this.userReact = this.userService.getCurrentUser();

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

    this.reactService.getReacts(this.pictureId, 0, 10).subscribe({
      next: (data) => {
        this.comments = data;
        this.comments.forEach((comment: any) => {
          // debugger
          comment.url = `${environment.apiBaseUrl}/pictures/url/${comment.profilePicture}`;
        });
      },
      complete: () => {
        console.log('Comments fetch completed');
      },
      error: (error) => {
        console.error('Error fetching comments:', error);
      }      
    });

    this.reactService.getTotalLike(this.pictureId,0,1000).subscribe({
      next: (data) => {
        this.pictureInfo.likes = data.totalUsers;
      }
    })
  }

  likePicture() {
    const reactDTO = new ReactDTO({
      user_id: this.userReact.id,
      picture_id: this.pictureId,
      content: ''
    });
    if (this.isLiked) {
      // Unlike
      this.reactService.unlike(reactDTO, this.pictureId).subscribe({
        next: () => {
          this.isLiked = false;
          this.pictureInfo.likes -= 1;
        },
        error: (error) => {
          console.error('Error unliking picture:', error);
        }
      });
    } else {
      // Like
      this.reactService.like(reactDTO).subscribe({
        next: () => {
          this.isLiked = true;
          this.pictureInfo.likes += 1;
        },
        error: (error) => {
          console.error('Error liking picture:', error);
        }
      });
    }
  }

  postComment() {
    const reactDTO = new ReactDTO({
      user_id: this.userReact.id,
      picture_id: this.pictureId,
      content: this.newComment
    });
    this.reactService.comment(reactDTO).subscribe({
      next: (comment) => {
        this.comments.push(comment);
        this.newComment = '';
      },
      error: (error) => {
        console.error('Error posting comment:', error);
      }
    });
  }

  downloadImage() {
    const link = document.createElement('a');
    link.href = this.pictureInfo.url;
    link.download = this.pictureInfo.url;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }
}
