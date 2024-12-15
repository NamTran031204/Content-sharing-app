import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PictureTag {
  id: number;
  tagName: string;
}

@Injectable({
  providedIn: 'root',
})
export class PictureTagService {
  private apiUrl = 'http://localhost:8080/api/v1/pictureTags'; // URL gốc của API back-end

  constructor(private http: HttpClient) {}

  // Lấy danh sách tag theo pictureId
  getTagsByPictureId(pictureId: number): Observable<PictureTag[]> {
    return this.http.get<PictureTag[]>(`${this.apiUrl}/${pictureId}`);
  }
}
