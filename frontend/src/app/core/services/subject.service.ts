import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../dto/response/api-response.dto';
import { SubjectResponse } from '../dto/response/subject-response.dto';
import { PageResponse } from '../dto/response/page-response.dto';
import { SubjectSearchRequest } from '../dto/request/subject-search-request.dto';

@Injectable({ providedIn: 'root' })
export class SubjectService {
  private http = inject(HttpClient);
  private baseUrl = environment.apiUrl;

  searchSubjects(params: SubjectSearchRequest): Observable<PageResponse<SubjectResponse>> {
    let httpParams = new HttpParams().set('page', params.page).set('size', params.size);
    if (params.code) httpParams = httpParams.set('code', params.code);
    if (params.name) httpParams = httpParams.set('name', params.name);

    return this.http
      .get<ApiResponse<PageResponse<SubjectResponse>>>(`${this.baseUrl}/subjects`, { params: httpParams })
      .pipe(map((res) => res.data));
  }
}