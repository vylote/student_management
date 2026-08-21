import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './core/services/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    /* angular mặc định dùng zone.js để auto re-render khi change detection
    Khi nhiều DOM event occur to much, ang gộp lại chạy 1 lần -> up performance
    hợp nhất event (eventCoalescing) */
    provideZoneChangeDetection({ eventCoalescing: true }),

    /* withComponentInputBinding(): cho phép Angular tự bind tham số route (vd :classId)
    thẳng vào @Input() của Component, không cần ActivatedRoute thủ công (Trang 5) */
    provideRouter(routes, withComponentInputBinding()),
    provideHttpClient(withInterceptors([authInterceptor])),
  ],
};
