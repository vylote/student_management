import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { StudentResponse } from '../dto/response/student-response.dto';
import { SubjectResponse } from '../dto/response/subject-response.dto';
import { ApiResponse } from '../dto/response/api-response.dto';
import { ScoreResponse } from '../dto/response/score-response.dto';
import { StudentSearchRequest } from '../dto/request/student-search-request.dto';
import { PageResponse } from '../dto/response/page-response.dto';
import { CreateStudentRequest } from '../dto/request/create-student-request.dto';
import { AssignAccountRequest } from '../dto/request/assign-account-request.dto';

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
      next: (data) => {
        this.students.set(data);
        this.loading.set(false);
      },
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
      .get<
        ApiResponse<SubjectResponse[]>
      >(`${this.baseUrl}/students/${studentId}/subjects`)
      .pipe(map((res) => res.data));
  }

  getMyScores(): Observable<ScoreResponse[]> {
    return this.http
      .get<ApiResponse<ScoreResponse[]>>(`${this.baseUrl}/students/scores`)
      .pipe(map((res) => res.data));
  }

  searchStudents(
    params: StudentSearchRequest,
  ): Observable<PageResponse<StudentResponse>> {
    let httpParams = new HttpParams()
      .set('page', params.page)
      .set('size', params.size);
    if (params.name) httpParams = httpParams.set('name', params.name);
    if (params.code) httpParams = httpParams.set('code', params.code);
    if (params.cohort) httpParams = httpParams.set('cohort', params.cohort);
    if (params.classroom)
      httpParams = httpParams.set('classroom', params.classroom);
    if (params.hasAccount !== undefined && params.hasAccount !== null) {
      httpParams = httpParams.set('hasAccount', String(params.hasAccount));
    }

    return this.http
      .get<
        ApiResponse<PageResponse<StudentResponse>>
      >(`${this.baseUrl}/students/search`, { params: httpParams })
      .pipe(map((res) => res.data));
  }

  addStudent(request: CreateStudentRequest): Observable<StudentResponse> {
    return this.http
      .post<ApiResponse<StudentResponse>>(`${this.baseUrl}/students`, request)
      .pipe(map((res) => res.data));
  }

  createAccount(
    code: string,
    request: AssignAccountRequest,
  ): Observable<StudentResponse> {
    return this.http
      .patch<
        ApiResponse<StudentResponse>
      >(`${this.baseUrl}/students/${code}/register`, request)
      .pipe(map((res) => res.data));
  }
}
