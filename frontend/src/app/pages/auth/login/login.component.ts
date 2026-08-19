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

    this.auth
      .login({ username: this.username(), password: this.password() })
      .subscribe({
        next: (res) => {
          this.auth.setSession(res);
          this.loading.set(false);
          this.router.navigateByUrl(this.auth.homeRouteForRole(res.user.role));
        },
        error: (err) => {
          this.loading.set(false);
          this.errorMessage.set(err.message ?? 'Đăng nhập thất bại');
        },
      });
  }

  fillDemo(username: string, password: string): void {
    this.username.set(username);
    this.password.set(password);
  }
}
