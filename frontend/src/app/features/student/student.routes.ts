import { Routes } from '@angular/router';
import { StudentDashboardComponent } from './pages/student-dashboard/student-dashboard.component';
import { StudentListComponent } from './pages/student-list/student-list.component';

export const STUDENT_ROUTES: Routes = [
  {
    path: '', // Khi truy cập vào /student, nó sẽ trỏ tới Dashboard
    component: StudentDashboardComponent
  },
  {
    path: 'list', // Khi truy cập vào /student, nó sẽ trỏ tới Dashboard
    component: StudentListComponent
  },
  // Bạn có thể thêm các route con khác ở đây (ví dụ: 'score', 'register-subject')
];