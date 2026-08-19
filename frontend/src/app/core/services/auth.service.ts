import { Injectable, computed, signal } from '@angular/core';
import { Observable, delay, of, throwError } from 'rxjs';
import { LoginRequest, LoginResponse, Role, User } from '../models/user.model';
import { MOCK_USERS } from '../mock-data/users.mock';

const TOKEN_KEY = 'sms_token';
const USER_KEY = 'sms_user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  // Signal lưu user hiện tại - toàn bộ UI (Header, Sidebar, Guard) đọc từ đây
  private currentUserSignal = signal<User | null>(this.readUserFromStorage());

  readonly currentUser = computed(() => this.currentUserSignal());
  readonly isLoggedIn = computed(() => !!this.currentUserSignal());
  readonly role = computed<Role | null>(() => this.currentUserSignal()?.role ?? null);

  /**
   * TODO: Khi có backend, thay nội dung hàm này bằng:
   * return this.http.post<LoginResponse>('/api/auth/login', payload);
   */
  login(payload: LoginRequest): Observable<LoginResponse> {
    const found = MOCK_USERS.find(
      (u) => u.username === payload.username && u.password === payload.password
    );

    if (!found) {
      return throwError(() => new Error('Sai tài khoản hoặc mật khẩu')).pipe(delay(400));
    }

    const { password, ...user } = found;
    const response: LoginResponse = {
      token: 'mock-jwt-token.' + btoa(user.username) + '.' + Date.now(),
      user,
    };

    return of(response).pipe(delay(500));
  }

  setSession(response: LoginResponse): void {
    localStorage.setItem(TOKEN_KEY, response.token);
    localStorage.setItem(USER_KEY, JSON.stringify(response.user));
    this.currentUserSignal.set(response.user);
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.currentUserSignal.set(null);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  homeRouteForRole(role: Role): string {
    switch (role) {
      case 'ROLE_PRINCIPAL':
        return '/principal/students';
      case 'ROLE_TEACHER':
        return '/teacher/classes';
      case 'ROLE_STUDENT':
        return '/student/my-scores';
    }
  }

  private readUserFromStorage(): User | null {
    const raw = localStorage.getItem(USER_KEY);
    return raw ? (JSON.parse(raw) as User) : null;
  }
}
