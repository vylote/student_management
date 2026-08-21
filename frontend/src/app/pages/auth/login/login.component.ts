import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  username = signal('');
  password = signal('');
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit(): void {
    if (!this.username() || !this.password()) {
      this.errorMessage.set('Vui lòng nhập đầy đủ tài khoản và mật khẩu.');
      return;
    }

    this.loading.set(true);
    this.errorMessage.set(null);

    // 
    this.auth.login({ username: this.username(), password: this.password() }).subscribe({
        next: () => {
          this.loading.set(false);
          // Lúc này setSession bên trong auth.login() đã chạy, signal đã có data
          const userRole = this.auth.role();
          this.router.navigateByUrl(this.auth.homeRouteForRole(userRole));
        },
        error: (err) => {
          this.loading.set(false);
          this.errorMessage.set(err.error?.msg || 'Đăng nhập thất bại');
        },
      });
  }
}