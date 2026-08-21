import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.isLoggedIn()) return true;
  router.navigate(['/auth/login']);
  return false;
};

export const roleGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  const allowedRoles = route.data['roles'] as string[] | undefined;

  if (!auth.isLoggedIn()) {
    router.navigate(['/auth/login']);
    return false;
  }

  const currentRole = auth.role();
  if (allowedRoles && currentRole && !allowedRoles.includes(currentRole)) {
    router.navigate([auth.homeRouteForRole(currentRole)]);
    return false;
  }

  return true;
};