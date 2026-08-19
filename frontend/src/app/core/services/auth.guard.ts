import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { Role } from '../models/user.model';

/** Chặn truy cập nếu chưa đăng nhập */
export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.isLoggedIn()) return true;
  router.navigate(['/auth/login']);
  return false;
};

/** Chặn truy cập nếu vai trò không khớp - dùng route data: { roles: ['ROLE_PRINCIPAL'] } */
export const roleGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const allowedRoles = route.data['roles'] as Role[] | undefined;

  if (!auth.isLoggedIn()) {
    router.navigate(['/auth/login']);
    return false;
  }

  if (allowedRoles && !allowedRoles.includes(auth.role()!)) {
    router.navigate([auth.homeRouteForRole(auth.role()!)]);
    return false;
  }

  return true;
};
