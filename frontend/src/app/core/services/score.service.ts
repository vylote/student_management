import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../dto/response/api-response.dto';
import { ScoreResponse } from '../dto/response/score-response.dto';
import { UpdateScoreRequest } from '../dto/request/update-score-request.dto';

@Injectable({ providedIn: 'root' })
export class ScoreService {
  private http = inject(HttpClient);
  private baseUrl = environment.apiUrl;

  getScoresByStudentId(studentId: number): Observable<ScoreResponse[]> {
    return this.http
      .get<ApiResponse<ScoreResponse[]>>(`${this.baseUrl}/scores/student/${studentId}`)
      .pipe(map((res) => res.data));
  }

  updateScore(request: UpdateScoreRequest): Observable<ScoreResponse> {
    return this.http
      .put<ApiResponse<ScoreResponse>>(`${this.baseUrl}/scores`, request)
      .pipe(map((res) => res.data));
  }
}