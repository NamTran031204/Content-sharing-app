import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { environment } from 'src/enviroments/environment';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  userAvatar: string = '';
  constructor(private router: Router, private userService: UserService) {}

  ngOnInit(): void {
    // Kiểm tra thông tin user trong localStorage
    const currentUser = this.userService.getCurrentUser();
    if (currentUser && currentUser.name) {
      this.isLoggedIn = true;
      this.userAvatar =`${environment.apiBaseUrl}/pictures/url/${currentUser.profilePicture}`;
    }
  }

  logout() {
    // Xóa thông tin user và cập nhật trạng thái
    this.userService.clearCurrentUser();
    alert('Đăng xuất thành công');
    this.isLoggedIn = false;
    this.router.navigate(['/']); // Quay về trang chủ
  }

}
