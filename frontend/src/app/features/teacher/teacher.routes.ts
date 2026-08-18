import { Routes } from '@angular/router';
import { TeacherDashboardComponent } from './pages/teacher-dashboard/teacher-dashboard.component';

export const TEACHER_ROUTES: Routes = [
  {
    path: '', 
    component: TeacherDashboardComponent
  }
];