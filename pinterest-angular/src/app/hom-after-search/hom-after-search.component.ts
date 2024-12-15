import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hom-after-search',
  templateUrl: './hom-after-search.component.html',
  styleUrls: ['./hom-after-search.component.scss']
})
export class HomAfterSearchComponent {
  constructor(private router: Router) {}
  
    goToImageInfo() {
      this.router.navigate(['/image-info']);
    }
}
