import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { authGuard, roleGuard } from './core/services/auth.guard';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'login',
        loadComponent: () =>
          import('./pages/auth/login/login.component').then((m) => m.LoginComponent),
      },
      { path: '', redirectTo: 'login', pathMatch: 'full' },
    ],
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'principal/students',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_PRINCIPAL'] },
        loadComponent: () =>
          import('./pages/principal/students/students.component').then((m) => m.StudentsComponent),
      },
      {
        path: 'principal/subjects-teachers',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_PRINCIPAL'] },
        loadComponent: () =>
          import('./pages/principal/subjects-teachers/subjects-teachers.component').then(
            (m) => m.SubjectsTeachersComponent
          ),
      },
      {
        path: 'teacher/classes',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_TEACHER'] },
        loadComponent: () =>
          import('./pages/teacher/classes/teacher-classes.component').then(
            (m) => m.TeacherClassesComponent
          ),
      },
      {
        path: 'teacher/classes/:classId/scores',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_TEACHER'] },
        loadComponent: () =>
          import('./pages/teacher/scores/class-scores.component').then(
            (m) => m.ClassScoresComponent
          ),
      },
      {
        path: 'student/my-scores',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_STUDENT'] },
        loadComponent: () =>
          import('./pages/student/my-scores/my-scores.component').then(
            (m) => m.MyScoresComponent
          ),
      },
      // TODO: chỗ này sau sửa lại redirect tới home theo role
      { path: '', redirectTo: '/auth/login', pathMatch: 'full' },
    ],
  },
  { path: '**', redirectTo: '/auth/login' },
];
