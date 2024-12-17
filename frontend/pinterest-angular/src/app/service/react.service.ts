import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReactService {
  private apiUrl = `${environment.apiBaseUrl}/reacts`;

  constructor(private http: HttpClient) {}

  createReact(reactDTO: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, reactDTO);
  }

  deleteReact(pictureId: number, reactDTO: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${pictureId}`, { body: reactDTO });
  }

  getReacts(pictureId: number, reactType: number, page: number, limit: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${pictureId}/${reactType}?page=${page}&limit=${limit}`);
  }
}