import { Routes } from '@angular/router';
import { HomePageComponent } from './features/home/pages/home-page/home-page.component';

export const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
    pathMatch: 'full'
  },
  {
    path: 'student',
    // Cú pháp Lazy Loading kiểu mới, tải file student.routes.ts khi cần
    loadChildren: () => import('./features/student/student.routes').then(m => m.STUDENT_ROUTES)
  },
  {
    path: 'teacher',
    loadChildren: () => import('./features/teacher/teacher.routes').then(m => m.TEACHER_ROUTES)
  }
];