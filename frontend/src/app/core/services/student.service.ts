import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { StudentResponse } from '../dto/response/student-response.dto';
import { SubjectResponse } from '../dto/response/subject-response.dto';
import { ApiResponse } from '../dto/response/api-response.dto';

@Injectable({ providedIn: 'root' })
export class StudentService {
  private http = inject(HttpClient);
  private baseUrl = environment.apiUrl;

  readonly students = signal<StudentResponse[]>([]);
  readonly loading = signal(false);

  getAllStudents(): Observable<StudentResponse[]> {
    return this.http
      .get<ApiResponse<StudentResponse[]>>(`${this.baseUrl}/students`)
      .pipe(map((res) => res.data));
  }

  loadStudents(): void {
    this.loading.set(true);
    this.getAllStudents().subscribe({
      next: (data) => { this.students.set(data); this.loading.set(false); },
      error: () => this.loading.set(false),
    });
  }

  getStudentById(id: number): Observable<StudentResponse> {
    return this.http
      .get<ApiResponse<StudentResponse>>(`${this.baseUrl}/students/${id}`)
      .pipe(map((res) => res.data));
  }

  // Tính năng 3: danh sách môn học sinh viên đã đăng ký (chưa kèm điểm)
  getSubjectsByStudentId(studentId: number): Observable<SubjectResponse[]> {
    return this.http
      .get<ApiResponse<SubjectResponse[]>>(`${this.baseUrl}/students/${studentId}/subjects`)
      .pipe(map((res) => res.data));
  }
}