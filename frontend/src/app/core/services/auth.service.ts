import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../dto/response/api-response.dto';
import { TokenResponse } from '../dto/response/token-response.dto';
import { LoginRequest } from '../dto/request/login-request.dto';
import { JwtPayload } from '../dto/jwt-payload.dto';
import { Account, Role } from '../models/account.model';

const TOKEN_KEY = 'jwt_token';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/accounts`;

  // Signal gốc đọc từ LocalStorage khi F5 tải lại trang
  private currentAccountSignal = signal<Account | null>(this.readAccountFromToken());

  // Các Computed Signals cung cấp data cho Header, Sidebar, Guard
  readonly currentAccount = computed(() => this.currentAccountSignal());
  readonly isLoggedIn = computed(() => !!this.currentAccountSignal());
  readonly role = computed<string | null>(() => this.currentAccountSignal()?.role ?? null);

  login(request: LoginRequest): Observable<ApiResponse<TokenResponse>> {
    // this.http.post trả về observable, lazy — chỉ bắn request khi gọi .subscribe()
    return this.http.post<ApiResponse<TokenResponse>>(`${this.apiUrl}/login`, request).pipe(
      // tap() != map(): map biến đổi giá trị trả cho luồng đi tiếp, tap là utility operator (side-effect)
      tap((res) => {
        if (res.code === '1000' && res.data?.token) {
          this.setSession(res.data.token);
        }
      })
    );
  }

  // localStorage: API trình duyệt thuần, dùng để lưu token sống sót qua F5 (reload trang)
  setSession(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
    this.currentAccountSignal.set(this.parseAccount(token));
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    this.currentAccountSignal.set(null);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  homeRouteForRole(role: string | null): string {
    switch (role) {
      case 'ROLE_PRINCIPAL':
        return '/principal/students';
      case 'ROLE_TEACHER':
        return '/teacher/classes';
      case 'ROLE_STUDENT':
        return '/student/my-scores';
      default:
        return '/auth/login';
    }
  }

  // Lớp cách ly duy nhất giữa JwtPayload (dto, do BE định nghĩa) và Account (model, FE tự dùng nội bộ).
  // Backend đổi claim trong token -> chỉ sửa đúng hàm này, không ảnh hưởng chỗ khác trong app.
  private parseAccount(token: string): Account {
    const decoded = jwtDecode<JwtPayload>(token);

    return {
      id: decoded.id,
      username: decoded.sub,
      role: decoded.role as Role,
    };
  }

  private readAccountFromToken(): Account | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      return this.parseAccount(token);
    } catch {
      return null;
    }
  }
}