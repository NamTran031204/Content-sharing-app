import { Component, OnInit } from '@angular/core';
import { PictureService } from '../service/picture.service';
import { Picture } from '../models/picture';
import { Tag } from '../models/tag';
import { environment } from 'src/enviroments/environment';
import { TrackByFunction } from '@angular/core';
import { PictureTagService } from '../service/picture-tag.service';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule, DOCUMENT } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { BaseComponent } from '../components/base/base.component';

//search
import { map, startWith } from 'rxjs/operators';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
//search


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  pictures: Picture[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 9;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = [];
  topTags: Tag[] = [];
  selectedPicture: Picture | null = null;

  //search
  searchControl = new FormControl('');
  filteredPictures: Observable<Picture[]> | undefined;
  //search

  constructor(
    private pictureService: PictureService,
    private pictureTagService: PictureTagService
  ) { }

  ngOnInit() {
    this.getPicture(this.currentPage, this.itemsPerPage);
    this.getTopTag();

    //search
    this.filteredPictures = this.searchControl.valueChanges.pipe(
      startWith(''),
      map(query => this.searchPictures(query || ''))
    );
    //search
  }

  //search
  searchPictures(query: string): Picture[] {
    this.pictureService.searchPictures(query).subscribe({
      next: (response: Picture[]) => {
        this.pictures = response;
      },
      error: (error: any) => {
        console.error('Error fetching pictures:', error);
      }
    });
    return this.pictures;
  }
  //search

  getPicture(page: number, limit: number) {
    this.pictureService.getPicture(page, limit).subscribe({
      next: (response: any) => {
        console.log('API Response:', response);
        if (response.pictureResponses && Array.isArray(response.pictureResponses)) {
          response.pictureResponses.forEach((picture: Picture) => {
            console.log('Picture:', picture); // Log each picture object to check for 'id' field
            console.log('Image URL:', picture.id);
            picture.url = `${environment.apiBaseUrl}/pictures/url/${picture.image_url}`;
          });
          this.pictures = response.pictureResponses;
          this.totalPages = response.totalPages;
          this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
        } else {
          console.warn('No pictures found in the response or response.pictureResponses is not an array');
        }
      },
      complete: () => {
        console.log('Request completed');
      },
      error: (error: any) => {
        console.error('Error: ', error);
      }
    });
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.getPicture(this.currentPage, this.itemsPerPage);
  }

  trackById(index: number, picture: any): number {
    return picture.id;
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const visiblePages: number[] = [];
    const range = 2; // Number of pages to show around the current page

    let start = Math.max(1, currentPage - range);
    let end = Math.min(totalPages, currentPage + range);

    for (let i = start; i <= end; i++) {
      visiblePages.push(i);
    }

    return visiblePages;
  }

  getTopTag(): void {
    this.pictureTagService.getTopTag().subscribe({
      next: (tags: any) => {
        this.topTags = tags;  // Assign the fetched tags to the topTags array
      },
      error: (err: any) => {
        console.error('Error fetching top tags:', err);
      }
    });

  }

  showPictureDetails(pictureId: number): void {
    this.pictureService.getPictureById(pictureId).subscribe({
      next: (data: Picture) => {
        debugger
        this.selectedPicture = data; // Store the selected picture details
        console.log('Picture details:', this.selectedPicture);
      },
      error: (err: any) => {
        console.error('Error fetching picture details:', err);
      }
    });
  }
}

