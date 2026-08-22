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

  constructor(
    private auth: AuthService,
    private router: Router,
  ) {}

  onSubmit(): void {
    if (!this.username() || !this.password()) {
      this.errorMessage.set('Vui lòng nhập đầy đủ tài khoản và mật khẩu.');
      return;
    }

    this.loading.set(true);
    this.errorMessage.set(null);

    // subcribe ở đây mới bắn req
    this.auth
      .login({ username: this.username(), password: this.password() })
      .subscribe({
        next: () => {
          this.loading.set(false);
          const userRole = this.auth.role();
          const target = this.auth.homeRouteForRole(userRole);
          console.log('role:', userRole, '-> target:', target);
          this.router.navigateByUrl(target).then((success) => {
            console.log('navigate success?', success);
          });
        },
        error: (err) => {
          this.loading.set(false);
          this.errorMessage.set(err.error?.msg || 'Đăng nhập thất bại');
        },
      });
  }
}
