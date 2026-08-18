import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'student',
    // Cú pháp Lazy Loading kiểu mới, tải file student.routes.ts khi cần
    loadChildren: () => import('./features/student/student.routes').then(m => m.STUDENT_ROUTES)
  },
  {
    path: 'teacher',
    loadChildren: () => import('./features/teacher/teacher.routes').then(m => m.TEACHER_ROUTES)
  },
  {
    path: '',
    redirectTo: '/student', // Mặc định mở web lên sẽ vào trang sinh viên
    pathMatch: 'full'
  }
];