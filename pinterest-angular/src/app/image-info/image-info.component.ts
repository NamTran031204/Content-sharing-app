import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PictureTagService, PictureTag } from '../picture-tag.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-image-info',
  templateUrl: './image-info.component.html',
  styleUrls: ['./image-info.component.scss'],
})
export class ImageInfoComponent implements OnInit {
  pictureId!: number; // ID của hình ảnh
  tags: PictureTag[] = []; // Danh sách tag

  constructor(
    private route: ActivatedRoute, // Lấy tham số từ URL
    private pictureTagService: PictureTagService
  ) {}

  ngOnInit(): void {
    // Lấy pictureId từ URL
    this.pictureId = Number(this.route.snapshot.paramMap.get('id'));

    // Gọi service để lấy tag
    this.pictureTagService.getTagsByPictureId(this.pictureId).subscribe(
      (data) => {
        this.tags = data; // Lưu danh sách tag
      },
      (error) => {
        console.error('Error fetching tags:', error);
      }
    );
  }
}
