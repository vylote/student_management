import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

const ROLE_LABEL: Record<string, string> = {
  ROLE_PRINCIPAL: 'Hiệu trưởng',
  ROLE_TEACHER: 'Giảng viên',
  ROLE_STUDENT: 'Sinh viên',
};

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  constructor(public auth: AuthService, private router: Router) {}

  get roleLabel(): string {
    const role = this.auth.role();
    return role ? ROLE_LABEL[role] : '';
  }

  logout(): void {
    this.auth.logout();
    this.router.navigateByUrl('/auth/login');
  }
}
