import { inject, Injectable } from '@angular/core';
import { PictureService } from 'src/app/service/picture.service';

@Injectable()
export class BaseComponent {
    pictureService : PictureService = inject(PictureService);
    

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
}
