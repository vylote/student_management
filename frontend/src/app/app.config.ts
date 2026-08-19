import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  // withComponentInputBinding(): cho phép Angular tự bind tham số route (vd :classId)
  // thẳng vào @Input() của Component, không cần ActivatedRoute thủ công (Trang 5)
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withComponentInputBinding()),
  ],
};
