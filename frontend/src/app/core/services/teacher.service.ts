import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../dto/response/api-response.dto';
import { SubjectResponse } from '../dto/response/subject-response.dto';
import { StudentResponse } from '../dto/response/student-response.dto';
import { PageResponse } from '../dto/response/page-response.dto';
import { TeacherSearchRequest } from '../dto/request/teacher-search-request.dto';
import { TeacherResponse } from '../dto/response/teacher-response.dto';
import { TeachingAssignmentRequest } from '../dto/request/teaching-assignment-request.dto';
import { TeachingAssignmentResponse } from '../dto/response/teaching-assignment-response.dto';
import { CreateTeacherRequest } from '../dto/request/create-teacher-request.dto';
import { AssignAccountRequest } from '../dto/request/assign-account-request.dto';

export interface StudentsListParams {
  subjectId: number;
  classroom: string;
  page: number;
  size: number;
}

@Injectable({ providedIn: 'root' })
export class TeacherService {
  private http = inject(HttpClient);
  private baseUrl = environment.apiUrl;

  getMySubjects(): Observable<SubjectResponse[]> {
    return this.http
      .get<
        ApiResponse<SubjectResponse[]>
      >(`${this.baseUrl}/teachers/my-subjects`)
      .pipe(map((res) => res.data));
  }

  getMyClassrooms(subjectId: number): Observable<string[]> {
    return this.http
      .get<
        ApiResponse<string[]>
      >(`${this.baseUrl}/teachers/${subjectId}/my-classes`)
      .pipe(map((res) => res.data));
  }

  getStudentsInClass(
    params: StudentsListParams,
  ): Observable<PageResponse<StudentResponse>> {
    const httpParams = new HttpParams()
      .set('subjectId', params.subjectId)
      .set('classroom', params.classroom)
      .set('page', params.page)
      .set('size', params.size);

    return this.http
      .get<
        ApiResponse<PageResponse<StudentResponse>>
      >(`${this.baseUrl}/teachers/my-class/students`, { params: httpParams })
      .pipe(map((res) => res.data));
  }

  // Dùng cho trang Hiệu trưởng: tìm giảng viên (phân trang)
  searchTeachers(
    params: TeacherSearchRequest,
  ): Observable<PageResponse<TeacherResponse>> {
    let httpParams = new HttpParams()
      .set('page', params.page)
      .set('size', params.size);
    if (params.code) httpParams = httpParams.set('code', params.code);
    if (params.fullName)
      httpParams = httpParams.set('fullName', params.fullName);
    if (params.gender) httpParams = httpParams.set('gender', params.gender);
    if (params.department)
      httpParams = httpParams.set('department', params.department);

    return this.http
      .get<
        ApiResponse<PageResponse<TeacherResponse>>
      >(`${this.baseUrl}/teachers`, { params: httpParams })
      .pipe(map((res) => res.data));
  }

  // Dùng cho trang Hiệu trưởng: phân công giảng viên dạy 1 môn ở 1 lớp
  assignTeaching(
    request: TeachingAssignmentRequest,
  ): Observable<TeachingAssignmentResponse> {
    return this.http
      .post<
        ApiResponse<TeachingAssignmentResponse>
      >(`${this.baseUrl}/teachers/assign`, request)
      .pipe(map((res) => res.data));
  }

  addTeacher(request: CreateTeacherRequest): Observable<TeacherResponse> {
    return this.http
      .post<ApiResponse<TeacherResponse>>(`${this.baseUrl}/teachers`, request)
      .pipe(map((res) => res.data));
  }

  createAccount(
    code: string,
    request: AssignAccountRequest,
  ): Observable<TeacherResponse> {
    return this.http
      .patch<
        ApiResponse<TeacherResponse>
      >(`${this.baseUrl}/teachers/${code}/register`, request)
      .pipe(map((res) => res.data));
  }
}
