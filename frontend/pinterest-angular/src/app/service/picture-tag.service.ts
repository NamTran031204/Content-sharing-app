import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/enviroments/environment';
import { Observable } from 'rxjs';
import { Tag } from '../models/tag';

@Injectable({
  providedIn: 'root'
})
export class PictureTagService {
  private apiPictureTagUrl = `${environment.apiBaseUrl}/pictureTags`;

  constructor(private http: HttpClient) { }

  getTopTag() :Observable<any>{
    return this.http.get<Tag[]>(`${this.apiPictureTagUrl}/topTag`)
  }
  getTagsByPictureId(pictureId: number): Observable<any> {
    return this.http.get<Tag[]>(`${this.apiPictureTagUrl}/${pictureId}`);
  }
}
